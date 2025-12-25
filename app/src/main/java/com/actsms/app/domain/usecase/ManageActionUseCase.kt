package com.actsms.app.domain.usecase

import com.actsms.app.domain.model.UserActionType
import com.actsms.app.domain.model.UserBehaviorData
import com.actsms.app.domain.repository.ActionRepository
import com.actsms.app.domain.repository.PreferencesRepository
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Use case for managing action state and recording user behavior
 */
class ManageActionUseCase @Inject constructor(
    private val actionRepository: ActionRepository,
    private val preferencesRepository: PreferencesRepository
) {
    /**
     * Mark action as completed and record behavior
     */
    suspend fun completeAction(actionId: String): Result<Unit> {
        return try {
            val action = actionRepository.getActionById(actionId)
                ?: return Result.failure(Exception("Action not found"))

            // Mark as completed
            actionRepository.markAsCompleted(actionId)

            // Record behavior for learning
            val behavior = UserBehaviorData(
                actionId = actionId,
                category = action.category,
                userAction = UserActionType.COMPLETED,
                timestamp = LocalDateTime.now(),
                reminderOffset = calculateReminderOffset(action)
            )
            preferencesRepository.recordUserBehavior(behavior)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Snooze action and record behavior
     */
    suspend fun snoozeAction(actionId: String, until: LocalDateTime): Result<Unit> {
        return try {
            val action = actionRepository.getActionById(actionId)
                ?: return Result.failure(Exception("Action not found"))

            actionRepository.snoozeAction(actionId, until)

            val behavior = UserBehaviorData(
                actionId = actionId,
                category = action.category,
                userAction = UserActionType.SNOOZED,
                timestamp = LocalDateTime.now()
            )
            preferencesRepository.recordUserBehavior(behavior)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Ignore action and record behavior
     */
    suspend fun ignoreAction(actionId: String): Result<Unit> {
        return try {
            val action = actionRepository.getActionById(actionId)
                ?: return Result.failure(Exception("Action not found"))

            actionRepository.ignoreAction(actionId)

            val behavior = UserBehaviorData(
                actionId = actionId,
                category = action.category,
                userAction = UserActionType.IGNORED,
                timestamp = LocalDateTime.now()
            )
            preferencesRepository.recordUserBehavior(behavior)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Delete action
     */
    suspend fun deleteAction(actionId: String): Result<Unit> {
        return actionRepository.deleteAction(actionId)
    }

    /**
     * Calculate how many minutes before due date the user acted
     */
    private fun calculateReminderOffset(action: com.actsms.app.domain.model.Action): Long? {
        val dueDate = action.dueDate ?: return null
        val now = LocalDateTime.now()
        return java.time.temporal.ChronoUnit.MINUTES.between(now, dueDate)
    }
}
