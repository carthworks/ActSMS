package com.actsms.app.domain.repository

import com.actsms.app.domain.model.SenderPreference
import com.actsms.app.domain.model.UserBehaviorData
import kotlinx.coroutines.flow.Flow

/**
 * Repository for user preferences and learning data
 */
interface PreferencesRepository {
    /**
     * Get sender preferences
     */
    fun getSenderPreference(sender: String): Flow<SenderPreference?>

    /**
     * Save sender preference
     */
    suspend fun saveSenderPreference(preference: SenderPreference)

    /**
     * Get all sender preferences
     */
    fun getAllSenderPreferences(): Flow<List<SenderPreference>>

    /**
     * Record user behavior for learning
     */
    suspend fun recordUserBehavior(behavior: UserBehaviorData)

    /**
     * Get user behavior history
     */
    fun getUserBehaviorHistory(): Flow<List<UserBehaviorData>>

    /**
     * Get optimal reminder offset for a category based on learning
     */
    suspend fun getOptimalReminderOffset(category: com.actsms.app.domain.model.ActionCategory): Long?

    /**
     * Check if onboarding is completed
     */
    suspend fun isOnboardingCompleted(): Boolean

    /**
     * Mark onboarding as completed
     */
    suspend fun setOnboardingCompleted(completed: Boolean)

    /**
     * Get last SMS scan timestamp
     */
    suspend fun getLastScanTimestamp(): java.time.LocalDateTime?

    /**
     * Update last SMS scan timestamp
     */
    suspend fun updateLastScanTimestamp(timestamp: java.time.LocalDateTime)
}
