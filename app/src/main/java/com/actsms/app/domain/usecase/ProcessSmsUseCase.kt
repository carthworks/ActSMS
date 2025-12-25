package com.actsms.app.domain.usecase

import com.actsms.app.domain.model.Action
import com.actsms.app.domain.model.ActionCategory
import com.actsms.app.domain.model.ParsedSmsData
import com.actsms.app.domain.model.SmsMessage
import com.actsms.app.domain.repository.ActionRepository
import com.actsms.app.domain.repository.ParsingRepository
import com.actsms.app.domain.repository.PreferencesRepository
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

/**
 * Use case for processing SMS and creating actions
 */
class ProcessSmsUseCase @Inject constructor(
    private val parsingRepository: ParsingRepository,
    private val actionRepository: ActionRepository,
    private val preferencesRepository: PreferencesRepository
) {
    /**
     * Process a single SMS message and create action if applicable
     */
    suspend operator fun invoke(sms: SmsMessage): Result<Action?> {
        return try {
            // Parse the SMS
            val parsedData = parsingRepository.parseSms(sms)
                ?: return Result.success(null) // Not actionable

            // Check confidence threshold
            val threshold = parsingRepository.getConfidenceThreshold()
            if (parsedData.confidence < threshold) {
                return Result.success(null) // Low confidence
            }

            // Check sender preferences
            val senderPref = preferencesRepository.getSenderPreference(sms.address)
            // Note: Flow needs to be collected, simplified here for use case
            
            // Calculate reminder time based on category and learning
            val reminderTime = calculateReminderTime(
                parsedData.category,
                parsedData.dueDate
            )

            // Create action
            val action = Action(
                id = UUID.randomUUID().toString(),
                smsId = sms.id,
                category = parsedData.category,
                title = parsedData.title,
                description = parsedData.description,
                amount = parsedData.amount,
                dueDate = parsedData.dueDate,
                reminderTime = reminderTime,
                sender = sms.address,
                urgencyScore = calculateUrgency(parsedData),
                confidence = parsedData.confidence,
                metadata = parsedData.metadata
            )

            // Check for duplicates
            val duplicates = actionRepository.findDuplicates(action)
            if (duplicates.isNotEmpty()) {
                return Result.success(null) // Duplicate exists
            }

            // Save action
            actionRepository.insertAction(action)
            
            Result.success(action)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Process multiple SMS messages in batch
     */
    suspend fun processBatch(messages: List<SmsMessage>): Result<List<Action>> {
        return try {
            val actions = messages.mapNotNull { sms ->
                invoke(sms).getOrNull()
            }
            Result.success(actions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Calculate reminder time based on category and due date
     */
    private suspend fun calculateReminderTime(
        category: ActionCategory,
        dueDate: LocalDateTime?
    ): LocalDateTime? {
        if (dueDate == null) return null

        // Get learned offset or use defaults
        val offsetMinutes = preferencesRepository.getOptimalReminderOffset(category)
            ?: getDefaultReminderOffset(category)

        return dueDate.minusMinutes(offsetMinutes)
    }

    /**
     * Get default reminder offset in minutes
     */
    private fun getDefaultReminderOffset(category: ActionCategory): Long {
        return when (category) {
            ActionCategory.BILL -> 2 * 24 * 60L      // 2 days before
            ActionCategory.EMI -> 3 * 24 * 60L       // 3 days before
            ActionCategory.DELIVERY -> 0L             // Same day
            ActionCategory.APPOINTMENT -> 60L         // 1 hour before
            ActionCategory.INFO -> 0L
            ActionCategory.UNKNOWN -> 24 * 60L       // 1 day before
        }
    }

    /**
     * Calculate urgency score (0.0 to 1.0)
     */
    private fun calculateUrgency(parsedData: ParsedSmsData): Float {
        var urgency = 0.5f

        // Increase urgency based on category
        urgency += when (parsedData.category) {
            ActionCategory.BILL -> 0.2f
            ActionCategory.EMI -> 0.3f
            ActionCategory.APPOINTMENT -> 0.2f
            ActionCategory.DELIVERY -> 0.1f
            else -> 0f
        }

        // Increase urgency if due date is soon
        parsedData.dueDate?.let { dueDate ->
            val hoursUntilDue = java.time.temporal.ChronoUnit.HOURS.between(
                LocalDateTime.now(),
                dueDate
            )
            when {
                hoursUntilDue < 24 -> urgency += 0.3f
                hoursUntilDue < 48 -> urgency += 0.2f
                hoursUntilDue < 72 -> urgency += 0.1f
            }
        }

        return urgency.coerceIn(0f, 1f)
    }
}
