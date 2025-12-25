package com.actsms.app.domain.usecase

import com.actsms.app.domain.model.Action
import com.actsms.app.domain.repository.ActionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

/**
 * Use case for retrieving actions for dashboard
 */
class GetActionsUseCase @Inject constructor(
    private val actionRepository: ActionRepository
) {
    /**
     * Get actions due today
     */
    fun getTodayActions(): Flow<List<Action>> {
        return actionRepository.getActionsDueToday()
    }

    /**
     * Get upcoming actions (next 7 days, excluding today)
     */
    fun getUpcomingActions(): Flow<List<Action>> {
        return actionRepository.getUpcomingActions()
    }

    /**
     * Get completed actions
     */
    fun getCompletedActions(): Flow<List<Action>> {
        return actionRepository.getCompletedActions()
    }

    /**
     * Get all pending actions
     */
    fun getPendingActions(): Flow<List<Action>> {
        return actionRepository.getActionsByStatus(
            com.actsms.app.domain.model.ActionStatus.PENDING
        )
    }

    /**
     * Get overdue actions
     */
    suspend fun getOverdueActions(): List<Action> {
        val now = LocalDateTime.now()
        return actionRepository.getAllActions()
            .let { flow ->
                // Collect flow and filter
                val allActions = mutableListOf<Action>()
                flow.collect { actions ->
                    allActions.addAll(actions.filter { action ->
                        action.dueDate?.isBefore(now) == true &&
                        action.status == com.actsms.app.domain.model.ActionStatus.PENDING
                    })
                }
                allActions
            }
    }
}
