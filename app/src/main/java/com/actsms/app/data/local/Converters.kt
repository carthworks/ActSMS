package com.actsms.app.data.local

import androidx.room.TypeConverter
import com.actsms.app.data.local.entity.UserActionType
import com.actsms.app.domain.model.ActionCategory
import com.actsms.app.domain.model.ActionStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Type converters for Room database
 * Handles conversion of complex types to/from database-compatible types
 */
class Converters {
    
    private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    
    // LocalDateTime converters
    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.format(dateTimeFormatter)
    }
    
    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, dateTimeFormatter) }
    }
    
    // ActionStatus converters
    @TypeConverter
    fun fromActionStatus(value: ActionStatus): String {
        return value.name
    }
    
    @TypeConverter
    fun toActionStatus(value: String): ActionStatus {
        return ActionStatus.valueOf(value)
    }
    
    // ActionCategory converters
    @TypeConverter
    fun fromActionCategory(value: ActionCategory): String {
        return value.name
    }
    
    @TypeConverter
    fun toActionCategory(value: String): ActionCategory {
        return ActionCategory.valueOf(value)
    }
    
    // UserActionType converters
    @TypeConverter
    fun fromUserActionType(value: UserActionType): String {
        return value.name
    }
    
    @TypeConverter
    fun toUserActionType(value: String): UserActionType {
        return UserActionType.valueOf(value)
    }
}
