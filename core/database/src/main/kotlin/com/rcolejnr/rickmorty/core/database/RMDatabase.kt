package com.rcolejnr.rickmorty.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rcolejnr.rickmorty.core.database.dao.RMCharacterRemoteKeysDao
import com.rcolejnr.rickmorty.core.database.dao.RMDao
import com.rcolejnr.rickmorty.core.database.dao.RMFtsDao
import com.rcolejnr.rickmorty.core.database.dao.RecentSearchQueryDao
import com.rcolejnr.rickmorty.core.database.model.RMCharacterEntity
import com.rcolejnr.rickmorty.core.database.model.RMCharacterFtsEntity
import com.rcolejnr.rickmorty.core.database.model.RMCharacterRemoteKeys
import com.rcolejnr.rickmorty.core.database.model.RMEpisodeEntity
import com.rcolejnr.rickmorty.core.database.model.RMLocationEntity
import com.rcolejnr.rickmorty.core.database.model.RecentSearchQueryEntity

@Database(
    entities = [
        RMCharacterEntity::class,
        RMEpisodeEntity::class,
        RMLocationEntity::class,
        RMCharacterRemoteKeys::class,
        RMCharacterFtsEntity::class,
        RecentSearchQueryEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class RMDatabase: RoomDatabase() {

    abstract val rmDao: RMDao
    abstract val rmFtsDao: RMFtsDao
    abstract val rsqDao: RecentSearchQueryDao
    abstract val rmRemoteKeys: RMCharacterRemoteKeysDao
}