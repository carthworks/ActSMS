package com.actsms.app.presentation.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.actsms.app.domain.model.Action
import com.actsms.app.domain.model.ActionStatus
import com.actsms.app.domain.usecase.GetActionsUseCase
import com.actsms.app.domain.usecase.ManageActionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Dashboard screen
 * Manages action lists and user interactions
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getActionsUseCase: GetActionsUseCase,
    private val manageActionUseCase: ManageActionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val _selectedTab = MutableStateFlow(DashboardTab.TODAY)
    val selectedTab: StateFlow<DashboardTab> = _selectedTab.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    init {
        loadActions()
    }

    /**
     * Load actions based on selected tab
     */
    fun loadActions() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading

            try {
                when (_selectedTab.value) {
                    DashboardTab.TODAY -> {
                        getActionsUseCase.getTodayActions()
                            .catch { error ->
                                _uiState.value = DashboardUiState.Error(
                                    error.message ?: "Failed to load today's actions"
                                )
                                showMessage("Error: ${error.message ?: "Failed to load today's actions"}")
                            }
                            .collect { actions ->
                                _uiState.value = if (actions.isEmpty()) {
                                    DashboardUiState.Empty("No actions for today")
                                } else {
                                    DashboardUiState.Success(actions)
                                }
                            }
                    }
                    DashboardTab.UPCOMING -> {
                        getActionsUseCase.getUpcomingActions()
                            .catch { error ->
                                _uiState.value = DashboardUiState.Error(
                                    error.message ?: "Failed to load upcoming actions"
                                )
                                showMessage("Error: ${error.message ?: "Failed to load upcoming actions"}")
                            }
                            .collect { actions ->
                                _uiState.value = if (actions.isEmpty()) {
                                    DashboardUiState.Empty("No upcoming actions")
                                } else {
                                    DashboardUiState.Success(actions)
                                }
                            }
                    }
                    DashboardTab.DONE -> {
                        getActionsUseCase.getCompletedActions()
                            .catch { error ->
                                _uiState.value = DashboardUiState.Error(
                                    error.message ?: "Failed to load completed actions"
                                )
                                showMessage("Error: ${error.message ?: "Failed to load completed actions"}")
                            }
                            .collect { actions ->
                                _uiState.value = if (actions.isEmpty()) {
                                    DashboardUiState.Empty("No completed actions")
                                } else {
                                    DashboardUiState.Success(actions)
                                }
                            }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(
                    e.message ?: "An unexpected error occurred"
                )
                showMessage("Error: ${e.message ?: "An unexpected error occurred"}")
            }
        }
    }

    /**
     * Switch between tabs
     */
    fun selectTab(tab: DashboardTab) {
        _selectedTab.value = tab
        loadActions()
    }

    /**
     * Mark action as completed
     */
    fun completeAction(actionId: String) {
        viewModelScope.launch {
            val result = manageActionUseCase.completeAction(actionId)
            result.onSuccess {
                showMessage("Action completed successfully ✓")
                loadActions() // Refresh list
            }.onFailure { error ->
                showMessage("Failed to complete action: ${error.message}")
            }
        }
    }

    /**
     * Dismiss action
     */
    fun dismissAction(actionId: String) {
        viewModelScope.launch {
            val result = manageActionUseCase.ignoreAction(actionId)
            result.onSuccess {
                showMessage("Action dismissed")
                loadActions() // Refresh list
            }.onFailure { error ->
                showMessage("Failed to dismiss action: ${error.message}")
            }
        }
    }

    /**
     * Snooze action (reschedule reminder)
     */
    fun snoozeAction(actionId: String, minutesFromNow: Int) {
        viewModelScope.launch {
            val until = java.time.LocalDateTime.now().plusMinutes(minutesFromNow.toLong())
            val result = manageActionUseCase.snoozeAction(actionId, until)
            result.onSuccess {
                showMessage("Action snoozed for $minutesFromNow minutes")
                loadActions() // Refresh list
            }.onFailure { error ->
                showMessage("Failed to snooze action: ${error.message}")
            }
        }
    }

    /**
     * Delete action permanently
     */
    fun deleteAction(actionId: String) {
        viewModelScope.launch {
            val result = manageActionUseCase.deleteAction(actionId)
            result.onSuccess {
                showMessage("Action deleted")
                loadActions() // Refresh list
            }.onFailure { error ->
                showMessage("Failed to delete action: ${error.message}")
            }
        }
    }

    /**
     * Refresh actions manually
     */
    fun refresh() {
        showMessage("Refreshing...")
        loadActions()
    }

    /**
     * Show snackbar message
     */
    private fun showMessage(message: String) {
        _snackbarMessage.value = message
    }

    /**
     * Clear snackbar message after it's shown
     */
    fun clearMessage() {
        _snackbarMessage.value = null
    }
}

/**
 * UI state for Dashboard screen
 */
sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(val actions: List<Action>) : DashboardUiState()
    data class Empty(val message: String) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}

/**
 * Dashboard tabs
 */
enum class DashboardTab {
    TODAY,
    UPCOMING,
    DONE
}
