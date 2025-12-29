package com.actsms.app.data.local.entity

/**
 * Types of user actions for learning system
 */
enum class UserActionType {
    ACCEPTED,       // User accepted the suggested action
    SNOOZED,        // User snoozed the reminder
    DISMISSED,      // User dismissed without action
    COMPLETED,      // User marked as completed
    IGNORED,        // User ignored the notification
    MODIFIED        // User modified the suggested action
}
