package com.actsms.app.domain.repository

import com.actsms.app.domain.model.ParsedSmsData
import com.actsms.app.domain.model.SmsMessage

/**
 * Repository interface for SMS parsing operations
 */
interface ParsingRepository {
    /**
     * Parse an SMS message and extract actionable data
     * @param sms The SMS message to parse
     * @return Parsed data with confidence score, or null if not actionable
     */
    suspend fun parseSms(sms: SmsMessage): ParsedSmsData?

    /**
     * Batch parse multiple SMS messages
     */
    suspend fun parseBatch(messages: List<SmsMessage>): List<Pair<SmsMessage, ParsedSmsData?>>

    /**
     * Get parsing confidence threshold
     */
    suspend fun getConfidenceThreshold(): Float

    /**
     * Update parsing confidence threshold
     */
    suspend fun setConfidenceThreshold(threshold: Float)
}
