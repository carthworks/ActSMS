package com.actsms.app.presentation.screens.onboarding

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

/**
 * Onboarding screen with permission request
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OnboardingScreen(
    onNavigateToDashboard: () -> Unit
) {
    val smsPermissionState = rememberPermissionState(
        permission = Manifest.permission.READ_SMS
    )

    // Navigate to dashboard if permission is already granted
    LaunchedEffect(smsPermissionState.status.isGranted) {
        if (smsPermissionState.status.isGranted) {
            onNavigateToDashboard()
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = "Privacy",
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Welcome to ActSMS",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your Privacy-First SMS Action Planner",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(48.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Why we need SMS permission:",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    PermissionReason(
                        "✓ Read transactional SMS (bills, EMIs, deliveries)"
                    )
                    PermissionReason(
                        "✓ Automatically create reminders and tasks"
                    )
                    PermissionReason(
                        "✓ All processing happens on your device"
                    )
                    PermissionReason(
                        "✓ No data is sent to any server"
                    )
                    PermissionReason(
                        "✓ No analytics, no tracking, no ads"
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    smsPermissionState.launchPermissionRequest()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = if (smsPermissionState.status.shouldShowRationale) {
                        "Grant Permission"
                    } else {
                        "Get Started"
                    },
                    style = MaterialTheme.typography.titleMedium
                )
            }

            if (smsPermissionState.status.shouldShowRationale) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "ActSMS needs SMS permission to function. Your privacy is our priority - all data stays on your device.",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun PermissionReason(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}
