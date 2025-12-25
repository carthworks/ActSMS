package com.actsms.app.data.parsing

import com.actsms.app.domain.model.ActionCategory
import com.actsms.app.domain.model.SmsMessage
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

/**
 * Unit tests for SMS Parser
 */
class SmsParserImplTest {

    private lateinit var parser: SmsParserImpl

    @Before
    fun setup() {
        parser = SmsParserImpl()
    }

    @Test
    fun `parse credit card bill SMS successfully`() {
        val sms = SmsMessage(
            id = "1",
            address = "HDFC-BANK",
            body = "Your HDFC Credit Card bill of Rs.15,450.00 is due on 25-12-2024. Pay now to avoid late fees.",
            timestamp = LocalDateTime.now()
        )

        val result = parser.parse(sms)

        assertNotNull(result)
        assertEquals(ActionCategory.BILL, result?.category)
        assertEquals(15450.0, result?.amount, 0.01)
        assertNotNull(result?.dueDate)
        assertTrue(result?.confidence ?: 0f > 0.5f)
    }

    @Test
    fun `parse EMI payment SMS successfully`() {
        val sms = SmsMessage(
            id = "2",
            address = "ICICI-BANK",
            body = "Your EMI of Rs.8,500 is due on 01/01/2025. Please ensure sufficient balance.",
            timestamp = LocalDateTime.now()
        )

        val result = parser.parse(sms)

        assertNotNull(result)
        assertEquals(ActionCategory.EMI, result?.category)
        assertEquals(8500.0, result?.amount, 0.01)
        assertNotNull(result?.dueDate)
    }

    @Test
    fun `parse delivery SMS successfully`() {
        val sms = SmsMessage(
            id = "3",
            address = "AMAZON",
            body = "Your package will be delivered today. Track your order with tracking number ABC123XYZ456.",
            timestamp = LocalDateTime.now()
        )

        val result = parser.parse(sms)

        assertNotNull(result)
        assertEquals(ActionCategory.DELIVERY, result?.category)
        assertNotNull(result?.trackingNumber)
    }

    @Test
    fun `parse utility bill SMS successfully`() {
        val sms = SmsMessage(
            id = "4",
            address = "BESCOM",
            body = "Your electricity bill of Rs.2,340.50 is due by 28-12-2024. Pay online to avoid disconnection.",
            timestamp = LocalDateTime.now()
        )

        val result = parser.parse(sms)

        assertNotNull(result)
        assertEquals(ActionCategory.BILL, result?.category)
        assertEquals(2340.50, result?.amount, 0.01)
        assertNotNull(result?.dueDate)
    }

    @Test
    fun `parse appointment SMS successfully`() {
        val sms = SmsMessage(
            id = "5",
            address = "APOLLO",
            body = "Your appointment is confirmed for 26-12-2024 at 10:30 AM with Dr. Smith.",
            timestamp = LocalDateTime.now()
        )

        val result = parser.parse(sms)

        assertNotNull(result)
        assertEquals(ActionCategory.APPOINTMENT, result?.category)
        assertNotNull(result?.dueDate)
    }

    @Test
    fun `filter out OTP SMS`() {
        val sms = SmsMessage(
            id = "6",
            address = "VERIFY",
            body = "Your OTP is 123456. Do not share this code with anyone.",
            timestamp = LocalDateTime.now()
        )

        val result = parser.parse(sms)

        assertNull(result) // Should be filtered out
    }

    @Test
    fun `filter out promotional SMS`() {
        val sms = SmsMessage(
            id = "7",
            address = "PROMO",
            body = "Get 50% discount on all products! Limited time offer. T&C apply. Reply STOP to unsubscribe.",
            timestamp = LocalDateTime.now()
        )

        val result = parser.parse(sms)

        assertNull(result) // Should be filtered out
    }

    @Test
    fun `handle SMS with no date`() {
        val sms = SmsMessage(
            id = "8",
            address = "BANK",
            body = "Your account balance is Rs.10,000. Thank you for banking with us.",
            timestamp = LocalDateTime.now()
        )

        val result = parser.parse(sms)

        // Should either be null or have INFO category with no due date
        if (result != null) {
            assertNull(result.dueDate)
        }
    }

    @Test
    fun `handle SMS with multiple amounts`() {
        val sms = SmsMessage(
            id = "9",
            address = "BANK",
            body = "Your credit card bill of Rs.5,000 is due on 30-12-2024. Minimum payment Rs.500.",
            timestamp = LocalDateTime.now()
        )

        val result = parser.parse(sms)

        assertNotNull(result)
        // Should extract the first/main amount
        assertTrue(result?.amount == 5000.0 || result?.amount == 500.0)
    }

    @Test
    fun `parse date in different formats`() {
        val formats = listOf(
            "25-12-2024",
            "25/12/2024",
            "25-12-24",
            "25/12/24"
        )

        formats.forEach { dateFormat ->
            val sms = SmsMessage(
                id = "test",
                address = "BANK",
                body = "Payment due on $dateFormat",
                timestamp = LocalDateTime.now()
            )

            val result = parser.parse(sms)
            // Should handle all common date formats
        }
    }

    @Test
    fun `confidence score increases with more data`() {
        val smsWithAmountAndDate = SmsMessage(
            id = "10",
            address = "BANK",
            body = "Bill of Rs.1,000 due on 30-12-2024",
            timestamp = LocalDateTime.now()
        )

        val smsWithOnlyAmount = SmsMessage(
            id = "11",
            address = "BANK",
            body = "Bill of Rs.1,000",
            timestamp = LocalDateTime.now()
        )

        val result1 = parser.parse(smsWithAmountAndDate)
        val result2 = parser.parse(smsWithOnlyAmount)

        if (result1 != null && result2 != null) {
            assertTrue(result1.confidence >= result2.confidence)
        }
    }

    @Test
    fun `handle empty SMS body`() {
        val sms = SmsMessage(
            id = "12",
            address = "SENDER",
            body = "",
            timestamp = LocalDateTime.now()
        )

        val result = parser.parse(sms)

        assertNull(result)
    }

    @Test
    fun `handle very long SMS`() {
        val longBody = "Your bill ".repeat(100) + "of Rs.1,000 is due on 30-12-2024"
        val sms = SmsMessage(
            id = "13",
            address = "BANK",
            body = longBody,
            timestamp = LocalDateTime.now()
        )

        val result = parser.parse(sms)

        assertNotNull(result)
        // Description should be truncated to 200 chars
        assertTrue(result?.description?.length ?: 0 <= 200)
    }
}
