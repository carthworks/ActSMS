package com.actsms.app.data.repository

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Telephony
import androidx.core.content.ContextCompat
import com.actsms.app.domain.model.SmsMessage
import com.actsms.app.domain.repository.SmsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

/**
 * Implementation of SmsRepository that reads SMS from device ContentProvider
 * Filters for transactional SMS and excludes OTPs and promotional messages
 */
class SmsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SmsRepository {

    private val contentResolver: ContentResolver = context.contentResolver

    companion object {
        private const val SMS_URI = "content://sms/inbox"
        
        // Transactional sender patterns (Indian SMS senders)
        private val TRANSACTIONAL_PATTERNS = listOf(
            Regex("^[A-Z]{2}-[A-Z]{6}$"),  // XX-XXXXXX format (e.g., HD-HDFCBK)
            Regex("^[A-Z]{6}$"),            // XXXXXX format (e.g., HDFCBK)
            Regex("^[A-Z]{2}[A-Z0-9]{4}$"), // XXXXXX format with numbers
            Regex("^\\d{5,6}$")             // 5-6 digit numbers
        )
        
        // Promotional keywords to exclude
        private val PROMO_KEYWORDS = listOf(
            "offer", "discount", "sale", "cashback", "deal",
            "unsubscribe", "reply stop", "t&c apply", "terms apply",
            "limited time", "hurry", "free", "win", "prize"
        )
    }

    override suspend fun hasPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun getAllSms(since: LocalDateTime?): Flow<List<SmsMessage>> = flow {
        if (!hasPermission()) {
            emit(emptyList())
            return@flow
        }

        val messages = mutableListOf<SmsMessage>()
        val cutoffTime = since?.let {
            it.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        } ?: 0L

        val cursor = contentResolver.query(
            Uri.parse(SMS_URI),
            arrayOf("_id", "address", "body", "date"),
            "date >= ?",
            arrayOf(cutoffTime.toString()),
            "date DESC"
        )

        cursor?.use {
            val idIndex = it.getColumnIndex("_id")
            val addressIndex = it.getColumnIndex("address")
            val bodyIndex = it.getColumnIndex("body")
            val dateIndex = it.getColumnIndex("date")

            while (it.moveToNext()) {
                val id = it.getString(idIndex)
                val address = it.getString(addressIndex) ?: continue
                val body = it.getString(bodyIndex) ?: continue
                val timestamp = it.getLong(dateIndex)

                // Filter for transactional SMS only
                if (isTransactionalSms(address, body)) {
                    messages.add(
                        SmsMessage(
                            id = id,
                            address = address,
                            body = body,
                            timestamp = LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(timestamp),
                                ZoneId.systemDefault()
                            )
                        )
                    )
                }
            }
        }

        emit(messages)
    }.flowOn(Dispatchers.IO)

    override fun getSmsBySender(sender: String): Flow<List<SmsMessage>> = flow {
        if (!hasPermission()) {
            emit(emptyList())
            return@flow
        }

        val messages = mutableListOf<SmsMessage>()

        val cursor = contentResolver.query(
            Uri.parse(SMS_URI),
            arrayOf("_id", "address", "body", "date"),
            "address = ?",
            arrayOf(sender),
            "date DESC"
        )

        cursor?.use {
            val idIndex = it.getColumnIndex("_id")
            val addressIndex = it.getColumnIndex("address")
            val bodyIndex = it.getColumnIndex("body")
            val dateIndex = it.getColumnIndex("date")

            while (it.moveToNext()) {
                messages.add(
                    SmsMessage(
                        id = it.getString(idIndex),
                        address = it.getString(addressIndex),
                        body = it.getString(bodyIndex),
                        timestamp = LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(it.getLong(dateIndex)),
                            ZoneId.systemDefault()
                        )
                    )
                )
            }
        }

        emit(messages)
    }.flowOn(Dispatchers.IO)

    override suspend fun getSmsById(id: String): SmsMessage? {
        if (!hasPermission()) return null

        val cursor = contentResolver.query(
            Uri.parse(SMS_URI),
            arrayOf("_id", "address", "body", "date"),
            "_id = ?",
            arrayOf(id),
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex("_id")
                val addressIndex = it.getColumnIndex("address")
                val bodyIndex = it.getColumnIndex("body")
                val dateIndex = it.getColumnIndex("date")

                return SmsMessage(
                    id = it.getString(idIndex),
                    address = it.getString(addressIndex),
                    body = it.getString(bodyIndex),
                    timestamp = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(it.getLong(dateIndex)),
                        ZoneId.systemDefault()
                    )
                )
            }
        }

        return null
    }

    override suspend fun filterTransactionalSms(messages: List<SmsMessage>): List<SmsMessage> {
        return messages.filter { sms ->
            isTransactionalSms(sms.address, sms.body)
        }
    }

    /**
     * Determine if SMS is transactional based on sender and content
     */
    private fun isTransactionalSms(sender: String, body: String): Boolean {
        // Check if sender matches transactional patterns
        val isTransactionalSender = TRANSACTIONAL_PATTERNS.any { pattern ->
            sender.matches(pattern)
        }

        // Exclude promotional messages
        val isPromo = PROMO_KEYWORDS.any { keyword ->
            body.contains(keyword, ignoreCase = true)
        }

        // Exclude OTPs (handled by parser, but double-check here)
        val isOtp = body.matches(Regex("(?i).*\\b\\d{4,6}\\b.*(otp|code|verification|verify).*"))

        return isTransactionalSender && !isPromo && !isOtp
    }
}
