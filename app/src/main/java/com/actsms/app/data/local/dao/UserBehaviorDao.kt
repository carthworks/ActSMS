package com.actsms.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.actsms.app.data.local.entity.UserActionType
import com.actsms.app.data.local.entity.UserBehaviorEntity
import com.actsms.app.domain.model.ActionCategory
import kotlinx.coroutines.flow.Flow

/**
 * DAO for UserBehavior entity
 * Tracks user behavior patterns for the learning system
 */
@Dao
interface UserBehaviorDao {
    
    @Insert
    suspend fun insert(behavior: UserBehaviorEntity)
    
    @Query("SELECT * FROM user_behavior WHERE actionId = :actionId ORDER BY actionTimestamp DESC")
    fun getByAction(actionId: String): Flow<List<UserBehaviorEntity>>
    
    @Query("SELECT * FROM user_behavior WHERE sender = :sender ORDER BY actionTimestamp DESC LIMIT :limit")
    fun getBySender(sender: String, limit: Int = 50): Flow<List<UserBehaviorEntity>>
    
    @Query("SELECT * FROM user_behavior WHERE category = :category ORDER BY actionTimestamp DESC LIMIT :limit")
    fun getByCategory(category: ActionCategory, limit: Int = 50): Flow<List<UserBehaviorEntity>>
    
    @Query("""
        SELECT AVG(reminderMinutesBeforeDue) 
        FROM user_behavior 
        WHERE category = :category 
        AND userAction IN ('ACCEPTED', 'COMPLETED')
        AND reminderMinutesBeforeDue IS NOT NULL
    """)
    suspend fun getAverageReminderTimeForCategory(category: ActionCategory): Double?
    
    @Query("""
        SELECT AVG(reminderMinutesBeforeDue) 
        FROM user_behavior 
        WHERE sender = :sender 
        AND userAction IN ('ACCEPTED', 'COMPLETED')
        AND reminderMinutesBeforeDue IS NOT NULL
    """)
    suspend fun getAverageReminderTimeForSender(sender: String): Double?
    
    @Query("""
        SELECT COUNT(*) * 100.0 / (SELECT COUNT(*) FROM user_behavior WHERE category = :category)
        FROM user_behavior 
        WHERE category = :category 
        AND userAction IN ('ACCEPTED', 'COMPLETED')
    """)
    suspend fun getAcceptanceRateForCategory(category: ActionCategory): Double?
    
    @Query("""
        SELECT COUNT(*) * 100.0 / (SELECT COUNT(*) FROM user_behavior WHERE sender = :sender)
        FROM user_behavior 
        WHERE sender = :sender 
        AND userAction IN ('ACCEPTED', 'COMPLETED')
    """)
    suspend fun getAcceptanceRateForSender(sender: String): Double?
    
    @Query("DELETE FROM user_behavior WHERE actionTimestamp < datetime('now', '-90 days')")
    suspend fun deleteOldRecords()
    
    @Query("SELECT COUNT(*) FROM user_behavior")
    suspend fun getCount(): Int
}
