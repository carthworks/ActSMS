package com.actsms.app.data.local.dao

import androidx.room.*
import com.actsms.app.data.local.entity.ActionEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for Action operations
 */
@Dao
interface ActionDao {
    @Query("SELECT * FROM actions ORDER BY createdAt DESC")
    fun getAllActions(): Flow<List<ActionEntity>>

    @Query("SELECT * FROM actions WHERE status = :status ORDER BY dueDate ASC")
    fun getActionsByStatus(status: String): Flow<List<ActionEntity>>

    @Query("""
        SELECT * FROM actions 
        WHERE status = 'PENDING' 
        AND dueDate IS NOT NULL 
        AND DATE(dueDate) = DATE('now', 'localtime')
        ORDER BY dueDate ASC
    """)
    fun getActionsDueToday(): Flow<List<ActionEntity>>

    @Query("""
        SELECT * FROM actions 
        WHERE status = 'PENDING' 
        AND dueDate IS NOT NULL 
        AND DATE(dueDate) BETWEEN DATE('now', 'localtime', '+1 day') AND DATE('now', 'localtime', '+7 days')
        ORDER BY dueDate ASC
    """)
    fun getUpcomingActions(): Flow<List<ActionEntity>>

    @Query("SELECT * FROM actions WHERE status = 'COMPLETED' ORDER BY completedAt DESC LIMIT 50")
    fun getCompletedActions(): Flow<List<ActionEntity>>

    @Query("SELECT * FROM actions WHERE id = :id")
    suspend fun getActionById(id: String): ActionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAction(action: ActionEntity)

    @Update
    suspend fun updateAction(action: ActionEntity)

    @Query("DELETE FROM actions WHERE id = :id")
    suspend fun deleteAction(id: String)

    @Query("UPDATE actions SET status = 'COMPLETED', completedAt = :completedAt WHERE id = :id")
    suspend fun markAsCompleted(id: String, completedAt: String)

    @Query("UPDATE actions SET status = 'SNOOZED', reminderTime = :until WHERE id = :id")
    suspend fun snoozeAction(id: String, until: String)

    @Query("UPDATE actions SET status = 'IGNORED' WHERE id = :id")
    suspend fun ignoreAction(id: String)

    @Query("""
        SELECT * FROM actions 
        WHERE category = :category 
        AND sender = :sender 
        AND ABS(JULIANDAY(dueDate) - JULIANDAY(:dueDate)) < 1
        AND status != 'IGNORED'
    """)
    suspend fun findSimilarActions(category: String, sender: String, dueDate: String?): List<ActionEntity>

    @Query("DELETE FROM actions WHERE status = 'COMPLETED' AND completedAt < :before")
    suspend fun deleteOldCompletedActions(before: String)
}
