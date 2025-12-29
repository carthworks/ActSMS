package com.actsms.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.actsms.app.domain.model.ActionCategory

/**
 * Entity to store user preferences for specific SMS senders
 */
@Entity(tableName = "sender_preferences")
data class SenderPreferenceEntity(
    @PrimaryKey
    val sender: String,
    val isBlocked: Boolean = false, // Block all SMS from this sender
    val autoAccept: Boolean = false, // Auto-accept actions from this sender
    val customReminderMinutes: Int? = null, // Custom reminder time (null = use default)
    val preferredCategory: ActionCategory? = null, // Override auto-detected category
    val notes: String? = null // User notes about this sender
)
