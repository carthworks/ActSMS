package com.actsms.app.domain.repository

import com.actsms.app.domain.model.SmsMessage
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * Repository interface for SMS operations
 */
interface SmsRepository {
    /**
     * Read all SMS messages from device
     * @param since Only fetch messages after this timestamp
     * @return Flow of SMS messages
     */
    fun getAllSms(since: LocalDateTime? = null): Flow<List<SmsMessage>>

    /**
     * Read SMS messages from a specific sender
     */
    fun getSmsBySender(sender: String): Flow<List<SmsMessage>>

    /**
     * Get a specific SMS by ID
     */
    suspend fun getSmsById(id: String): SmsMessage?

    /**
     * Check if SMS read permission is granted
     */
    suspend fun hasPermission(): Boolean

    /**
     * Filter transactional SMS (exclude OTPs, spam, promos)
     */
    suspend fun filterTransactionalSms(messages: List<SmsMessage>): List<SmsMessage>
}
