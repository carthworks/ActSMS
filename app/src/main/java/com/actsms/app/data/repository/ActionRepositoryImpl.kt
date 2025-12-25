package com.actsms.app.data.repository

import com.actsms.app.data.local.dao.ActionDao
import com.actsms.app.data.local.entity.toDomain
import com.actsms.app.data.local.entity.toEntity
import com.actsms.app.domain.model.Action
import com.actsms.app.domain.model.ActionStatus
import com.actsms.app.domain.repository.ActionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of ActionRepository using Room database
 */
@Singleton
class ActionRepositoryImpl @Inject constructor(
    private val actionDao: ActionDao
) : ActionRepository {

    override fun getAllActions(): Flow<List<Action>> {
        return actionDao.getAllActions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getActionsByStatus(status: ActionStatus): Flow<List<Action>> {
        return actionDao.getActionsByStatus(status.name).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getActionsDueToday(): Flow<List<Action>> {
        return actionDao.getActionsDueToday().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getUpcomingActions(): Flow<List<Action>> {
        return actionDao.getUpcomingActions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getCompletedActions(): Flow<List<Action>> {
        return actionDao.getCompletedActions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getActionById(id: String): Action? {
        return actionDao.getActionById(id)?.toDomain()
    }

    override suspend fun insertAction(action: Action): Result<String> {
        return try {
            actionDao.insertAction(action.toEntity())
            Result.success(action.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateAction(action: Action): Result<Unit> {
        return try {
            actionDao.updateAction(action.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAction(actionId: String): Result<Unit> {
        return try {
            actionDao.deleteAction(actionId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun markAsCompleted(actionId: String): Result<Unit> {
        return try {
            val now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            actionDao.markAsCompleted(actionId, now)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun snoozeAction(actionId: String, until: LocalDateTime): Result<Unit> {
        return try {
            val untilStr = until.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            actionDao.snoozeAction(actionId, untilStr)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun ignoreAction(actionId: String): Result<Unit> {
        return try {
            actionDao.ignoreAction(actionId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun findDuplicates(action: Action): List<Action> {
        return try {
            val dueDateStr = action.dueDate?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            actionDao.findSimilarActions(
                category = action.category.name,
                sender = action.sender,
                dueDate = dueDateStr
            ).map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
