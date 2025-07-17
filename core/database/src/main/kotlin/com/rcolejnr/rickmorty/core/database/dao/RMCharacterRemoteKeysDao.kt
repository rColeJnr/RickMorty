package com.rcolejnr.rickmorty.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rcolejnr.rickmorty.core.database.model.RMCharacterRemoteKeys

@Dao
interface RMCharacterRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RMCharacterRemoteKeys>)

    @Query("SELECT * FROM rm_character_remote_keys WHERE id = :id")
    suspend fun remoteKeysId(id: Int): RMCharacterRemoteKeys?

    @Query("DELETE FROM rm_character_remote_keys")
    suspend fun clearRemoteKeys()
}