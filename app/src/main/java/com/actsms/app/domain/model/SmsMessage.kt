package com.actsms.app.domain.model

import java.time.LocalDateTime

/**
 * Domain model representing an SMS message
 */
data class SmsMessage(
    val id: String,
    val address: String,
    val body: String,
    val timestamp: LocalDateTime,
    val isRead: Boolean = false,
    val threadId: String? = null
)
