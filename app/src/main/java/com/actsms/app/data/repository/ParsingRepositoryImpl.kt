package com.actsms.app.data.repository

import com.actsms.app.data.parsing.SmsParserImpl
import com.actsms.app.domain.model.ParsedSmsData
import com.actsms.app.domain.model.SmsMessage
import com.actsms.app.domain.repository.ParsingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of ParsingRepository
 * Wraps SmsParserImpl and provides repository interface
 */
@Singleton
class ParsingRepositoryImpl @Inject constructor(
    private val smsParser: SmsParserImpl
) : ParsingRepository {

    // Default confidence threshold
    private var confidenceThreshold: Float = 0.3f

    override suspend fun parseSms(sms: SmsMessage): ParsedSmsData? = withContext(Dispatchers.Default) {
        val result = smsParser.parse(sms)
        // Filter by confidence threshold
        if (result != null && result.confidence >= confidenceThreshold) {
            result
        } else {
            null
        }
    }

    override suspend fun parseBatch(messages: List<SmsMessage>): List<Pair<SmsMessage, ParsedSmsData?>> = 
        withContext(Dispatchers.Default) {
            messages.map { sms ->
                val parsed = smsParser.parse(sms)
                val filtered = if (parsed != null && parsed.confidence >= confidenceThreshold) {
                    parsed
                } else {
                    null
                }
                sms to filtered
            }
        }

    override suspend fun getConfidenceThreshold(): Float {
        return confidenceThreshold
    }

    override suspend fun setConfidenceThreshold(threshold: Float) {
        confidenceThreshold = threshold.coerceIn(0f, 1f)
    }
}
