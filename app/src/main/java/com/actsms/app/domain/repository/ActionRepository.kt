package com.actsms.app.domain.repository

import com.actsms.app.domain.model.Action
import com.actsms.app.domain.model.ActionStatus
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * Repository interface for Action operations
 */
interface ActionRepository {
    /**
     * Get all actions
     */
    fun getAllActions(): Flow<List<Action>>

    /**
     * Get actions by status
     */
    fun getActionsByStatus(status: ActionStatus): Flow<List<Action>>

    /**
     * Get actions due today
     */
    fun getActionsDueToday(): Flow<List<Action>>

    /**
     * Get upcoming actions (next 7 days)
     */
    fun getUpcomingActions(): Flow<List<Action>>

    /**
     * Get completed actions
     */
    fun getCompletedActions(): Flow<List<Action>>

    /**
     * Get action by ID
     */
    suspend fun getActionById(id: String): Action?

    /**
     * Insert a new action
     */
    suspend fun insertAction(action: Action): Result<String>

    /**
     * Update an existing action
     */
    suspend fun updateAction(action: Action): Result<Unit>

    /**
     * Delete an action
     */
    suspend fun deleteAction(actionId: String): Result<Unit>

    /**
     * Mark action as completed
     */
    suspend fun markAsCompleted(actionId: String): Result<Unit>

    /**
     * Snooze an action
     */
    suspend fun snoozeAction(actionId: String, until: LocalDateTime): Result<Unit>

    /**
     * Mark action as ignored
     */
    suspend fun ignoreAction(actionId: String): Result<Unit>

    /**
     * Check for duplicate actions
     */
    suspend fun findDuplicates(action: Action): List<Action>
}
