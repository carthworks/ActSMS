package com.actsms.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.actsms.app.data.local.dao.ActionDao
import com.actsms.app.data.local.entity.ActionEntity

/**
 * Main Room database for ActSMS
 * Uses SQLCipher for encryption
 */
@Database(
    entities = [
        ActionEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class ActSmsDatabase : RoomDatabase() {
    abstract fun actionDao(): ActionDao

    companion object {
        const val DATABASE_NAME = "actsms_database"
    }
}
