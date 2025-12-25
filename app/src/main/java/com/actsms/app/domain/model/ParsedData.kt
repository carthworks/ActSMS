package com.actsms.app.domain.model

/**
 * Result of SMS parsing operation
 */
data class ParsedSmsData(
    val category: ActionCategory,
    val title: String,
    val description: String,
    val amount: Double? = null,
    val dueDate: java.time.LocalDateTime? = null,
    val confidence: Float,
    val metadata: Map<String, String> = emptyMap(),
    val trackingNumber: String? = null,
    val accountNumber: String? = null,
    val referenceNumber: String? = null
)

/**
 * Represents a parsing rule match
 */
data class ParsingRule(
    val name: String,
    val pattern: Regex,
    val category: ActionCategory,
    val priority: Int = 0,
    val extractor: (MatchResult) -> ParsedSmsData?
)

/**
 * User preferences for a specific sender
 */
data class SenderPreference(
    val sender: String,
    val shouldCreateAction: Boolean = true,
    val customReminderOffset: Long? = null, // Minutes before due date
    val preferredCategory: ActionCategory? = null
)

/**
 * Learning data for adaptive behavior
 */
data class UserBehaviorData(
    val actionId: String,
    val category: ActionCategory,
    val userAction: UserActionType,
    val timestamp: java.time.LocalDateTime,
    val reminderOffset: Long? = null // How many minutes before due date user acted
)

enum class UserActionType {
    ACCEPTED,
    SNOOZED,
    IGNORED,
    COMPLETED
}
