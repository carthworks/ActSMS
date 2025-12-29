package com.actsms.app.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.actsms.app.domain.model.Action
import com.actsms.app.domain.model.ActionCategory
import com.actsms.app.domain.model.ActionStatus
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Card component to display an action with quick action buttons
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionCard(
    action: Action,
    onComplete: () -> Unit,
    onDismiss: () -> Unit,
    onSnooze: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showActions by remember { mutableStateOf(false) }

    Card(
        onClick = {
            showActions = !showActions
            onClick()
        },
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (action.status) {
                ActionStatus.PENDING -> MaterialTheme.colorScheme.surfaceVariant
                ActionStatus.SNOOZED -> MaterialTheme.colorScheme.tertiaryContainer
                ActionStatus.COMPLETED -> MaterialTheme.colorScheme.primaryContainer
                ActionStatus.IGNORED -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ActionStatus.EXPIRED -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.7f)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Icon
                CategoryIcon(category = action.category)
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Title and Sender
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = action.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "From: ${action.sender}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                // Status Badge
                StatusBadge(status = action.status)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Description
            if (action.description.isNotBlank()) {
                Text(
                    text = action.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Amount (if present)
            action.amount?.let { amount ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "₹${String.format("%.2f", amount)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Due Date
            action.dueDate?.let { dueDate ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Due: ${dueDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT))}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            
            // Quick Actions (expandable)
            AnimatedVisibility(visible = showActions && action.status == ActionStatus.PENDING) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Complete Button
                        FilledTonalButton(
                            onClick = onComplete,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Done")
                        }
                        
                        // Snooze Button
                        OutlinedButton(
                            onClick = onSnooze,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Snooze,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Snooze")
                        }
                        
                        // Dismiss Button
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Dismiss")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryIcon(category: ActionCategory) {
    val (icon, tint) = when (category) {
        ActionCategory.BILL -> Icons.Default.Receipt to MaterialTheme.colorScheme.error
        ActionCategory.EMI -> Icons.Default.CreditCard to MaterialTheme.colorScheme.tertiary
        ActionCategory.DELIVERY -> Icons.Default.LocalShipping to MaterialTheme.colorScheme.primary
        ActionCategory.APPOINTMENT -> Icons.Default.Event to MaterialTheme.colorScheme.secondary
        else -> Icons.Default.Info to MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    Icon(
        imageVector = icon,
        contentDescription = category.name,
        tint = tint,
        modifier = Modifier.size(32.dp)
    )
}

@Composable
private fun StatusBadge(status: ActionStatus) {
    val (text, color) = when (status) {
        ActionStatus.PENDING -> "Pending" to MaterialTheme.colorScheme.primary
        ActionStatus.SNOOZED -> "Snoozed" to MaterialTheme.colorScheme.tertiary
        ActionStatus.COMPLETED -> "Done" to MaterialTheme.colorScheme.secondary
        ActionStatus.IGNORED -> "Ignored" to MaterialTheme.colorScheme.onSurfaceVariant
        ActionStatus.EXPIRED -> "Expired" to MaterialTheme.colorScheme.error
    }
    
    Surface(
        color = color.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}
