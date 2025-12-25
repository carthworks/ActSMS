package com.actsms.app.di

import android.content.Context
import androidx.room.Room
import com.actsms.app.data.local.ActSmsDatabase
import com.actsms.app.data.local.dao.ActionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

/**
 * Hilt module for database dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ActSmsDatabase {
        // Generate encryption key (in production, use Android Keystore)
        val passphrase = SQLiteDatabase.getBytes("ActSMS_Secure_Key_2024".toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            context,
            ActSmsDatabase::class.java,
            ActSmsDatabase.DATABASE_NAME
        )
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration() // For development only
            .build()
    }

    @Provides
    @Singleton
    fun provideActionDao(database: ActSmsDatabase): ActionDao {
        return database.actionDao()
    }
}
