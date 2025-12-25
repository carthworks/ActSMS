package com.actsms.app.data.parsing

import com.actsms.app.domain.model.ActionCategory
import com.actsms.app.domain.model.ParsedSmsData
import com.actsms.app.domain.model.SmsMessage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

/**
 * SMS Parser implementation using regex patterns
 * Focused on Indian SMS formats
 */
class SmsParserImpl @Inject constructor() {
    
    private val patterns = listOf(
        // Credit Card Bill
        ParsingPattern(
            category = ActionCategory.BILL,
            regex = Regex(
                """(?i).*credit card.*(?:bill|payment).*(?:due|payable).*?(?:rs\.?|inr|₹)\s*([0-9,]+(?:\.[0-9]{2})?).*?(?:by|before|on)\s*(\d{1,2}[-/]\d{1,2}[-/]\d{2,4})""",
                RegexOption.DOT_MATCHES_ALL
            ),
            priority = 10
        ),
        
        // EMI Payment
        ParsingPattern(
            category = ActionCategory.EMI,
            regex = Regex(
                """(?i).*emi.*(?:due|payable).*?(?:rs\.?|inr|₹)\s*([0-9,]+(?:\.[0-9]{2})?).*?(?:by|on|before)\s*(\d{1,2}[-/]\d{1,2}[-/]\d{2,4})""",
                RegexOption.DOT_MATCHES_ALL
            ),
            priority = 10
        ),
        
        // Delivery/Shipment
        ParsingPattern(
            category = ActionCategory.DELIVERY,
            regex = Regex(
                """(?i).*(deliver|dispatch|ship).*(?:today|tomorrow|on)\s*(\d{1,2}[-/]\d{1,2}[-/]\d{2,4})?.*(?:track|tracking).*?([A-Z0-9]{8,})""",
                RegexOption.DOT_MATCHES_ALL
            ),
            priority = 9
        ),
        
        // Utility Bill
        ParsingPattern(
            category = ActionCategory.BILL,
            regex = Regex(
                """(?i).*(electricity|water|gas|broadband|mobile).*bill.*?(?:rs\.?|inr|₹)\s*([0-9,]+(?:\.[0-9]{2})?).*?(?:due|pay.*by)\s*(\d{1,2}[-/]\d{1,2}[-/]\d{2,4})""",
                RegexOption.DOT_MATCHES_ALL
            ),
            priority = 9
        ),
        
        // Appointment
        ParsingPattern(
            category = ActionCategory.APPOINTMENT,
            regex = Regex(
                """(?i).*(appointment|booking|reservation).*(?:on|for)\s*(\d{1,2}[-/]\d{1,2}[-/]\d{2,4}).*?(?:at)?\s*(\d{1,2}:\d{2}\s*(?:am|pm)?)""",
                RegexOption.DOT_MATCHES_ALL
            ),
            priority = 8
        ),
        
        // Generic Payment Due
        ParsingPattern(
            category = ActionCategory.BILL,
            regex = Regex(
                """(?i).*(?:payment|amount).*?(?:rs\.?|inr|₹)\s*([0-9,]+(?:\.[0-9]{2})?).*?(?:due|payable).*?(?:by|on|before)\s*(\d{1,2}[-/]\d{1,2}[-/]\d{2,4})""",
                RegexOption.DOT_MATCHES_ALL
            ),
            priority = 5
        )
    )

    /**
     * Parse SMS and extract actionable data
     */
    fun parse(sms: SmsMessage): ParsedSmsData? {
        // Filter out OTPs and promotional messages
        if (isOtp(sms.body) || isPromo(sms.body)) {
            return null
        }

        // Try each pattern in priority order
        val sortedPatterns = patterns.sortedByDescending { it.priority }
        
        for (pattern in sortedPatterns) {
            val match = pattern.regex.find(sms.body)
            if (match != null) {
                return extractData(sms, pattern.category, match)
            }
        }

        return null
    }

    /**
     * Extract structured data from regex match
     */
    private fun extractData(
        sms: SmsMessage,
        category: ActionCategory,
        match: MatchResult
    ): ParsedSmsData? {
        try {
            val amount = extractAmount(match)
            val dueDate = extractDate(match)
            val trackingNumber = extractTrackingNumber(match)
            
            val title = generateTitle(category, amount, dueDate)
            val description = sms.body.take(200) // First 200 chars
            
            val confidence = calculateConfidence(match, category)
            
            return ParsedSmsData(
                category = category,
                title = title,
                description = description,
                amount = amount,
                dueDate = dueDate,
                confidence = confidence,
                trackingNumber = trackingNumber,
                metadata = mapOf(
                    "sender" to sms.address,
                    "originalBody" to sms.body
                )
            )
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * Extract amount from match
     */
    private fun extractAmount(match: MatchResult): Double? {
        return try {
            match.groups[1]?.value
                ?.replace(",", "")
                ?.toDoubleOrNull()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Extract and parse date from match
     */
    private fun extractDate(match: MatchResult): LocalDateTime? {
        return try {
            val dateStr = match.groups.drop(1).firstNotNullOfOrNull { group ->
                group?.value?.takeIf { it.contains(Regex("""\d{1,2}[-/]\d{1,2}""")) }
            } ?: return null

            parseDate(dateStr)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Parse date string to LocalDateTime
     */
    private fun parseDate(dateStr: String): LocalDateTime? {
        val formats = listOf(
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd-MM-yy"),
            DateTimeFormatter.ofPattern("dd/MM/yy")
        )

        for (formatter in formats) {
            try {
                val date = java.time.LocalDate.parse(dateStr, formatter)
                return date.atTime(23, 59) // Default to end of day
            } catch (e: DateTimeParseException) {
                continue
            }
        }

        return null
    }

    /**
     * Extract tracking number
     */
    private fun extractTrackingNumber(match: MatchResult): String? {
        return match.groups.lastOrNull()?.value?.takeIf { 
            it.length >= 8 && it.matches(Regex("[A-Z0-9]+"))
        }
    }

    /**
     * Generate user-friendly title
     */
    private fun generateTitle(
        category: ActionCategory,
        amount: Double?,
        dueDate: LocalDateTime?
    ): String {
        return when (category) {
            ActionCategory.BILL -> {
                val amountStr = amount?.let { "₹${String.format("%.2f", it)}" } ?: ""
                "Bill Payment ${amountStr}".trim()
            }
            ActionCategory.EMI -> {
                val amountStr = amount?.let { "₹${String.format("%.2f", it)}" } ?: ""
                "EMI Payment ${amountStr}".trim()
            }
            ActionCategory.DELIVERY -> "Package Delivery"
            ActionCategory.APPOINTMENT -> "Appointment"
            else -> "Action Required"
        }
    }

    /**
     * Calculate parsing confidence
     */
    private fun calculateConfidence(match: MatchResult, category: ActionCategory): Float {
        var confidence = 0.5f

        // Increase confidence if we found amount
        if (match.groups.size > 1 && match.groups[1]?.value != null) {
            confidence += 0.2f
        }

        // Increase confidence if we found date
        if (match.groups.size > 2 && match.groups[2]?.value != null) {
            confidence += 0.2f
        }

        // Category-specific boost
        confidence += 0.1f

        return confidence.coerceIn(0f, 1f)
    }

    /**
     * Check if SMS is an OTP
     */
    private fun isOtp(body: String): Boolean {
        return body.matches(Regex("""(?i).*\b\d{4,6}\b.*(?:otp|code|verification|verify).*"""))
    }

    /**
     * Check if SMS is promotional
     */
    private fun isPromo(body: String): Boolean {
        val promoKeywords = listOf(
            "offer", "discount", "sale", "cashback", "deal",
            "unsubscribe", "reply stop", "t&c apply"
        )
        return promoKeywords.any { body.contains(it, ignoreCase = true) }
    }
}

/**
 * Data class for parsing patterns
 */
private data class ParsingPattern(
    val category: ActionCategory,
    val regex: Regex,
    val priority: Int
)
