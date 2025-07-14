package com.rcolejnr.rickmorty.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DatabaseModule {

    @Provides
    @Singleton
    fun provideRMDatabase(@ApplicationContext context: Context): RMDatabase =
        Room.databaseBuilder(
            context,
            RMDatabase::class.java,
            name = "rmapp-database-test",
        ).build()

}