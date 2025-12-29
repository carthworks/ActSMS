package com.actsms.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.actsms.app.data.local.entity.SenderPreferenceEntity
import com.actsms.app.domain.model.ActionCategory
import kotlinx.coroutines.flow.Flow

/**
 * DAO for SenderPreference entity
 * Manages user preferences and rules for specific SMS senders
 */
@Dao
interface SenderPreferenceDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(preference: SenderPreferenceEntity)
    
    @Update
    suspend fun update(preference: SenderPreferenceEntity)
    
    @Query("SELECT * FROM sender_preferences WHERE sender = :sender")
    suspend fun getBySender(sender: String): SenderPreferenceEntity?
    
    @Query("SELECT * FROM sender_preferences WHERE sender = :sender")
    fun observeBySender(sender: String): Flow<SenderPreferenceEntity?>
    
    @Query("SELECT * FROM sender_preferences WHERE isBlocked = 1")
    fun getBlockedSenders(): Flow<List<SenderPreferenceEntity>>
    
    @Query("SELECT * FROM sender_preferences WHERE autoAccept = 1")
    fun getAutoAcceptSenders(): Flow<List<SenderPreferenceEntity>>
    
    @Query("SELECT * FROM sender_preferences ORDER BY sender ASC")
    fun getAllPreferences(): Flow<List<SenderPreferenceEntity>>
    
    @Query("DELETE FROM sender_preferences WHERE sender = :sender")
    suspend fun delete(sender: String)
    
    @Query("SELECT customReminderMinutes FROM sender_preferences WHERE sender = :sender")
    suspend fun getCustomReminderTime(sender: String): Int?
    
    @Query("SELECT preferredCategory FROM sender_preferences WHERE sender = :sender")
    suspend fun getPreferredCategory(sender: String): ActionCategory?
}
