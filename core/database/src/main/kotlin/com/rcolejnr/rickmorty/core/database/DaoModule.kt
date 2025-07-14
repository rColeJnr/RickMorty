package com.rcolejnr.rickmorty.core.database

import com.rcolejnr.rickmorty.core.database.dao.RMCharacterRemoteKeysDao
import com.rcolejnr.rickmorty.core.database.dao.RMDao
import com.rcolejnr.rickmorty.core.database.dao.RMFtsDao
import com.rcolejnr.rickmorty.core.database.dao.RecentSearchQueryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideRMDao(database: RMDatabase): RMDao =
        database.rmDao

    @Provides
    fun providesRMFtsDao(database: RMDatabase): RMFtsDao =
        database.rmFtsDao

    @Provides
    fun provideRSQDao(database: RMDatabase): RecentSearchQueryDao =
        database.rsqDao

    @Provides
    fun provideRMRemoteKeys(database: RMDatabase): RMCharacterRemoteKeysDao =
        database.rmRemoteKeys
}