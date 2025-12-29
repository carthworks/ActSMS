package com.actsms.app.presentation.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.actsms.app.domain.model.Action
import com.actsms.app.presentation.components.ActionCard

/**
 * Main dashboard screen with tabs for Today/Upcoming/Done
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToWelcome: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToActionDetail: (String) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val snackbarMessage by viewModel.snackbarMessage.collectAsState()
    
    var showSnoozeDialog by remember { mutableStateOf(false) }
    var snoozeActionId by remember { mutableStateOf<String?>(null) }
    
    val snackbarHostState = remember { SnackbarHostState() }

    // Show snackbar when message changes
    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ActSMS") },
                actions = {
                    IconButton(onClick = onNavigateToWelcome) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    }
                    IconButton(onClick = { viewModel.refresh() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tab Row
            TabRow(
                selectedTabIndex = selectedTab.ordinal,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Tab(
                    selected = selectedTab == DashboardTab.TODAY,
                    onClick = { viewModel.selectTab(DashboardTab.TODAY) },
                    text = { Text("Today") }
                )
                Tab(
                    selected = selectedTab == DashboardTab.UPCOMING,
                    onClick = { viewModel.selectTab(DashboardTab.UPCOMING) },
                    text = { Text("Upcoming") }
                )
                Tab(
                    selected = selectedTab == DashboardTab.DONE,
                    onClick = { viewModel.selectTab(DashboardTab.DONE) },
                    text = { Text("Done") }
                )
            }

            // Tab Content
            when (uiState) {
                is DashboardUiState.Loading -> {
                    LoadingContent()
                }
                is DashboardUiState.Success -> {
                    val actions = (uiState as DashboardUiState.Success).actions
                    ActionListContent(
                        actions = actions,
                        onActionClick = onNavigateToActionDetail,
                        onComplete = { viewModel.completeAction(it) },
                        onDismiss = { viewModel.dismissAction(it) },
                        onSnooze = { actionId ->
                            snoozeActionId = actionId
                            showSnoozeDialog = true
                        },
                        onRefresh = { viewModel.refresh() }
                    )
                }
                is DashboardUiState.Empty -> {
                    EmptyStateContent(
                        message = (uiState as DashboardUiState.Empty).message
                    )
                }
                is DashboardUiState.Error -> {
                    ErrorContent(
                        message = (uiState as DashboardUiState.Error).message,
                        onRetry = { viewModel.refresh() }
                    )
                }
            }
        }
    }
    
    // Snooze Dialog
    if (showSnoozeDialog && snoozeActionId != null) {
        SnoozeDialog(
            onDismiss = { 
                showSnoozeDialog = false
                snoozeActionId = null
            },
            onConfirm = { minutes ->
                viewModel.snoozeAction(snoozeActionId!!, minutes)
                showSnoozeDialog = false
                snoozeActionId = null
            }
        )
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ActionListContent(
    actions: List<Action>,
    onActionClick: (String) -> Unit,
    onComplete: (String) -> Unit,
    onDismiss: (String) -> Unit,
    onSnooze: (String) -> Unit,
    onRefresh: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = actions,
            key = { it.id }
        ) { action ->
            ActionCard(
                action = action,
                onComplete = { onComplete(action.id) },
                onDismiss = { onDismiss(action.id) },
                onSnooze = { onSnooze(action.id) },
                onClick = { onActionClick(action.id) }
            )
        }
    }
}

@Composable
private fun EmptyStateContent(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "No Actions",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "Error",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Composable
private fun SnoozeDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var selectedMinutes by remember { mutableStateOf(60) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Snooze Reminder") },
        text = {
            Column {
                Text("Remind me in:")
                Spacer(modifier = Modifier.height(16.dp))
                
                val options = listOf(
                    15 to "15 minutes",
                    30 to "30 minutes",
                    60 to "1 hour",
                    120 to "2 hours",
                    1440 to "1 day"
                )
                
                options.forEach { (minutes, label) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = selectedMinutes == minutes,
                            onClick = { selectedMinutes = minutes }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(label)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(selectedMinutes) }) {
                Text("Snooze")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
