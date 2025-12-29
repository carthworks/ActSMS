package com.actsms.app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.actsms.app.domain.model.ActionCategory
import com.actsms.app.domain.model.SenderPreference
import com.actsms.app.domain.model.UserBehaviorData
import com.actsms.app.domain.repository.PreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of PreferencesRepository using DataStore
 * Manages user preferences and app settings
 */
@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesRepository {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "actsms_preferences")

    companion object {
        private val KEY_ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        private val KEY_LAST_SCAN_TIMESTAMP = stringPreferencesKey("last_scan_timestamp")
    }

    // Sender preferences - Stub implementation (should use Room DAO)
    override fun getSenderPreference(sender: String): Flow<SenderPreference?> {
        return flowOf(null) // TODO: Implement with SenderPreferenceDao
    }

    override suspend fun saveSenderPreference(preference: SenderPreference) {
        // TODO: Implement with SenderPreferenceDao
    }

    override fun getAllSenderPreferences(): Flow<List<SenderPreference>> {
        return flowOf(emptyList()) // TODO: Implement with SenderPreferenceDao
    }

    // User behavior - Stub implementation (should use Room DAO)
    override suspend fun recordUserBehavior(behavior: UserBehaviorData) {
        // TODO: Implement with UserBehaviorDao
    }

    override fun getUserBehaviorHistory(): Flow<List<UserBehaviorData>> {
        return flowOf(emptyList()) // TODO: Implement with UserBehaviorDao
    }

    override suspend fun getOptimalReminderOffset(category: ActionCategory): Long? {
        // TODO: Implement learning algorithm with UserBehaviorDao
        return null
    }

    // Onboarding
    override suspend fun isOnboardingCompleted(): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_ONBOARDING_COMPLETED] ?: false
        }.first()
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_ONBOARDING_COMPLETED] = completed
        }
    }

    // Last scan timestamp
    override suspend fun getLastScanTimestamp(): LocalDateTime? {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_LAST_SCAN_TIMESTAMP]?.let {
                LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            }
        }.first()
    }

    override suspend fun updateLastScanTimestamp(timestamp: LocalDateTime) {
        context.dataStore.edit { preferences ->
            preferences[KEY_LAST_SCAN_TIMESTAMP] = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
    }
}
