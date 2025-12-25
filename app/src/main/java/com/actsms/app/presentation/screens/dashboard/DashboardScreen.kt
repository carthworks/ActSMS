package com.actsms.app.presentation.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Main dashboard screen with tabs for Today/Upcoming/Done
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToActionDetail: (String) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Today", "Upcoming", "Done")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ActSMS") },
                actions = {
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tab Row
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            // Tab Content
            when (selectedTab) {
                0 -> TodayTab(onActionClick = onNavigateToActionDetail)
                1 -> UpcomingTab(onActionClick = onNavigateToActionDetail)
                2 -> DoneTab(onActionClick = onNavigateToActionDetail)
            }
        }
    }
}

@Composable
private fun TodayTab(onActionClick: (String) -> Unit) {
    // TODO: Implement with ViewModel and actual data
    EmptyStateContent(
        title = "No actions for today",
        message = "You're all caught up! Check upcoming for future actions."
    )
}

@Composable
private fun UpcomingTab(onActionClick: (String) -> Unit) {
    // TODO: Implement with ViewModel and actual data
    EmptyStateContent(
        title = "No upcoming actions",
        message = "We'll automatically create actions from your SMS messages."
    )
}

@Composable
private fun DoneTab(onActionClick: (String) -> Unit) {
    // TODO: Implement with ViewModel and actual data
    EmptyStateContent(
        title = "No completed actions",
        message = "Completed actions will appear here."
    )
}

@Composable
private fun EmptyStateContent(
    title: String,
    message: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
