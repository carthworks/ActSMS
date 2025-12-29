package com.actsms.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * Entity to track processed SMS messages to prevent duplicate action creation
 */
@Entity(tableName = "processed_sms")
data class ProcessedSmsEntity(
    @PrimaryKey
    val smsId: String,
    val sender: String,
    val bodyHash: String, // Hash of SMS body for duplicate detection
    val processedAt: LocalDateTime,
    val actionCreated: Boolean, // Whether an action was created from this SMS
    val actionId: String? = null // Reference to created action (if any)
)
