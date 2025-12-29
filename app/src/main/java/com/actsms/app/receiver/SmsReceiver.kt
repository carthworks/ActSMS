package com.actsms.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.actsms.app.domain.model.SmsMessage
import com.actsms.app.domain.usecase.ProcessSmsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Broadcast receiver for incoming SMS messages
 * Processes SMS in real-time as they arrive
 */
@AndroidEntryPoint
class SmsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var processSmsUseCase: ProcessSmsUseCase

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    companion object {
        private const val TAG = "SmsReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            return
        }

        Log.d(TAG, "SMS received, processing...")

        // Extract SMS messages from intent
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        
        if (messages.isNullOrEmpty()) {
            Log.w(TAG, "No messages found in intent")
            return
        }

        // Process each SMS message
        messages.forEach { smsMessage ->
            val sender = smsMessage.displayOriginatingAddress ?: smsMessage.originatingAddress ?: ""
            val body = smsMessage.messageBody ?: ""
            
            if (sender.isBlank() || body.isBlank()) {
                Log.w(TAG, "Invalid SMS: empty sender or body")
                return@forEach
            }

            val message = SmsMessage(
                id = "${sender}_${System.currentTimeMillis()}", // Generate unique ID
                address = sender,
                body = body,
                timestamp = LocalDateTime.now()
            )

            // Process SMS asynchronously
            scope.launch {
                try {
                    val result = processSmsUseCase(message)
                    result.onSuccess { action ->
                        if (action != null) {
                            Log.d(TAG, "Action created: ${action.title} from $sender")
                            // TODO: Show notification for new action
                        } else {
                            Log.d(TAG, "SMS filtered out (OTP/Promo/Duplicate): $sender")
                        }
                    }.onFailure { error ->
                        Log.e(TAG, "Error processing SMS: ${error.message}", error)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Exception processing SMS: ${e.message}", e)
                }
            }
        }
    }
}
