package com.actsms.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.actsms.app.domain.model.ActionCategory
import java.time.LocalDateTime

/**
 * Entity to track user behavior for learning system
 * Helps adapt reminder timing and action suggestions based on user patterns
 */
@Entity(tableName = "user_behavior")
data class UserBehaviorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val actionId: String,
    val category: ActionCategory,
    val sender: String,
    val userAction: UserActionType, // What user did with the action
    val actionTimestamp: LocalDateTime, // When user took the action
    val reminderMinutesBeforeDue: Int?, // How early the reminder was set
    val wasOnTime: Boolean? = null // Whether action was completed before due date
)
