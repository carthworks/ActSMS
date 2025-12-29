package com.actsms.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.actsms.app.data.local.entity.ProcessedSmsEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for ProcessedSms entity
 * Tracks which SMS messages have been processed to prevent duplicates
 */
@Dao
interface ProcessedSmsDao {
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(processedSms: ProcessedSmsEntity): Long
    
    @Query("SELECT EXISTS(SELECT 1 FROM processed_sms WHERE smsId = :smsId)")
    suspend fun isProcessed(smsId: String): Boolean
    
    @Query("SELECT EXISTS(SELECT 1 FROM processed_sms WHERE bodyHash = :bodyHash AND sender = :sender)")
    suspend fun isDuplicate(sender: String, bodyHash: String): Boolean
    
    @Query("SELECT * FROM processed_sms WHERE sender = :sender ORDER BY processedAt DESC LIMIT :limit")
    fun getBySender(sender: String, limit: Int = 10): Flow<List<ProcessedSmsEntity>>
    
    @Query("SELECT * FROM processed_sms WHERE actionCreated = 1 ORDER BY processedAt DESC")
    fun getAllWithActions(): Flow<List<ProcessedSmsEntity>>
    
    @Query("DELETE FROM processed_sms WHERE processedAt < datetime('now', '-30 days')")
    suspend fun deleteOldRecords()
    
    @Query("SELECT COUNT(*) FROM processed_sms")
    suspend fun getCount(): Int
}
