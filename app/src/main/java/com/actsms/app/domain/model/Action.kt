package com.actsms.app.domain.model

import java.time.LocalDateTime

/**
 * Domain model representing an actionable item created from SMS
 */
data class Action(
    val id: String,
    val smsId: String,
    val category: ActionCategory,
    val title: String,
    val description: String,
    val amount: Double? = null,
    val dueDate: LocalDateTime? = null,
    val reminderTime: LocalDateTime? = null,
    val sender: String,
    val urgencyScore: Float = 0.5f, // 0.0 to 1.0
    val confidence: Float = 0.0f,   // Parsing confidence 0.0 to 1.0
    val status: ActionStatus = ActionStatus.PENDING,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val completedAt: LocalDateTime? = null,
    val metadata: Map<String, String> = emptyMap() // Flexible metadata storage
)

/**
 * Status of an action
 */
enum class ActionStatus {
    PENDING,    // Not yet acted upon
    SNOOZED,    // User snoozed the reminder
    COMPLETED,  // User marked as done
    IGNORED,    // User explicitly ignored
    EXPIRED     // Past due date without action
}

/**
 * Extension function to check if action is actionable
 */
fun Action.isActionable(): Boolean {
    return status == ActionStatus.PENDING || status == ActionStatus.SNOOZED
}

/**
 * Extension function to check if action is overdue
 */
fun Action.isOverdue(): Boolean {
    val now = LocalDateTime.now()
    return dueDate?.isBefore(now) == true && status == ActionStatus.PENDING
}

/**
 * Extension function to get days until due
 */
fun Action.daysUntilDue(): Int? {
    val due = dueDate ?: return null
    val now = LocalDateTime.now()
    return java.time.temporal.ChronoUnit.DAYS.between(now.toLocalDate(), due.toLocalDate()).toInt()
}
