package com.actsms.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.actsms.app.domain.model.Action
import com.actsms.app.domain.model.ActionCategory
import com.actsms.app.domain.model.ActionStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Room entity for Action
 */
@Entity(tableName = "actions")
data class ActionEntity(
    @PrimaryKey
    val id: String,
    val smsId: String,
    val category: String,
    val title: String,
    val description: String,
    val amount: Double?,
    val dueDate: String?,
    val reminderTime: String?,
    val sender: String,
    val urgencyScore: Float,
    val confidence: Float,
    val status: String,
    val createdAt: String,
    val completedAt: String?,
    val metadata: String // JSON string
)

/**
 * Convert domain Action to entity
 */
fun Action.toEntity(): ActionEntity {
    return ActionEntity(
        id = id,
        smsId = smsId,
        category = category.name,
        title = title,
        description = description,
        amount = amount,
        dueDate = dueDate?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        reminderTime = reminderTime?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        sender = sender,
        urgencyScore = urgencyScore,
        confidence = confidence,
        status = status.name,
        createdAt = createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        completedAt = completedAt?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        metadata = metadataToJson(metadata)
    )
}

/**
 * Convert entity to domain Action
 */
fun ActionEntity.toDomain(): Action {
    return Action(
        id = id,
        smsId = smsId,
        category = ActionCategory.valueOf(category),
        title = title,
        description = description,
        amount = amount,
        dueDate = dueDate?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME) },
        reminderTime = reminderTime?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME) },
        sender = sender,
        urgencyScore = urgencyScore,
        confidence = confidence,
        status = ActionStatus.valueOf(status),
        createdAt = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        completedAt = completedAt?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME) },
        metadata = jsonToMetadata(metadata)
    )
}

/**
 * Simple JSON conversion for metadata (can be replaced with proper JSON library)
 */
private fun metadataToJson(metadata: Map<String, String>): String {
    return metadata.entries.joinToString(",") { "${it.key}:${it.value}" }
}

private fun jsonToMetadata(json: String): Map<String, String> {
    if (json.isEmpty()) return emptyMap()
    return json.split(",").associate {
        val parts = it.split(":")
        parts[0] to parts.getOrElse(1) { "" }
    }
}
