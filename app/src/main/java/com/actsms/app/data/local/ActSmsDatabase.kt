package com.actsms.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.actsms.app.data.local.dao.ActionDao
import com.actsms.app.data.local.dao.ProcessedSmsDao
import com.actsms.app.data.local.dao.SenderPreferenceDao
import com.actsms.app.data.local.dao.UserBehaviorDao
import com.actsms.app.data.local.entity.ActionEntity
import com.actsms.app.data.local.entity.ProcessedSmsEntity
import com.actsms.app.data.local.entity.SenderPreferenceEntity
import com.actsms.app.data.local.entity.UserBehaviorEntity

/**
 * Main Room database for ActSMS
 * Uses SQLCipher for encryption
 */
@Database(
    entities = [
        ActionEntity::class,
        ProcessedSmsEntity::class,
        SenderPreferenceEntity::class,
        UserBehaviorEntity::class
    ],
    version = 2, // Incremented version for new entities
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class ActSmsDatabase : RoomDatabase() {
    abstract fun actionDao(): ActionDao
    abstract fun processedSmsDao(): ProcessedSmsDao
    abstract fun senderPreferenceDao(): SenderPreferenceDao
    abstract fun userBehaviorDao(): UserBehaviorDao

    companion object {
        const val DATABASE_NAME = "actsms_database"
    }
}
