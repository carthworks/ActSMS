package com.actsms.app.domain.model

/**
 * Categories of actionable SMS messages
 */
enum class ActionCategory {
    BILL,           // Utility bills, credit card bills
    EMI,            // Loan EMIs, installments
    DELIVERY,       // Package deliveries, food orders
    APPOINTMENT,    // Doctor appointments, meetings
    INFO,           // General information
    UNKNOWN         // Could not be categorized
}

/**
 * Extension function to get user-friendly display name
 */
fun ActionCategory.displayName(): String = when (this) {
    ActionCategory.BILL -> "Bill Payment"
    ActionCategory.EMI -> "EMI Payment"
    ActionCategory.DELIVERY -> "Delivery"
    ActionCategory.APPOINTMENT -> "Appointment"
    ActionCategory.INFO -> "Information"
    ActionCategory.UNKNOWN -> "Other"
}

/**
 * Extension function to get icon name for UI
 */
fun ActionCategory.iconName(): String = when (this) {
    ActionCategory.BILL -> "receipt"
    ActionCategory.EMI -> "account_balance"
    ActionCategory.DELIVERY -> "local_shipping"
    ActionCategory.APPOINTMENT -> "event"
    ActionCategory.INFO -> "info"
    ActionCategory.UNKNOWN -> "help_outline"
}
