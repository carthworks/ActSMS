package com.actsms.app.di

import com.actsms.app.data.repository.ActionRepositoryImpl
import com.actsms.app.domain.repository.ActionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for repository bindings
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindActionRepository(
        impl: ActionRepositoryImpl
    ): ActionRepository

    // Add other repository bindings as they are implemented
    // @Binds
    // @Singleton
    // abstract fun bindSmsRepository(impl: SmsRepositoryImpl): SmsRepository
    
    // @Binds
    // @Singleton
    // abstract fun bindParsingRepository(impl: ParsingRepositoryImpl): ParsingRepository
    
    // @Binds
    // @Singleton
    // abstract fun bindPreferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository
}
