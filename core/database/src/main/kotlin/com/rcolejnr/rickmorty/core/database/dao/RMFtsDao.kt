package com.rcolejnr.rickmorty.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.rcolejnr.rickmorty.core.database.model.RMCharacterFtsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RMFtsDao {
    @Upsert
    suspend fun upsertCharacters(characters: List<RMCharacterFtsEntity>)

    @Query("SELECT characterId FROM character_fts_table WHERE character_fts_table MATCH :query")
    fun searchAllCharacters(query: String): Flow<List<Int>>

    @Query("SELECT count(*) FROM character_fts_table")
    fun getCount(): Flow<Int>
}