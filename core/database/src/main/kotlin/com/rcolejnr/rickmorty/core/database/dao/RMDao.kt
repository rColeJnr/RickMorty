package com.rcolejnr.rickmorty.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rcolejnr.rickmorty.core.database.model.RMCharacterEntity
import com.rcolejnr.rickmorty.core.database.model.RMEpisodeEntity
import com.rcolejnr.rickmorty.core.database.model.RMLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RMDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<RMCharacterEntity>)

    @Query(
        value = """
            DELETE FROM character_table WHERE id in (:ids)
        """
    )
    suspend fun clearCharacters(ids: List<Int>)

    @Query(
        value = """
            SELECT * FROM character_table 
            WHERE 
                CASE WHEN :filterByName
                    THEN name LIKE :name
                    ELSE 1
                END
            AND
                CASE WHEN :filterByStatus
                    THEN status LIKE :status
                    ELSE 1
                END
            AND
                CASE WHEN :filterByGender
                    THEN gender LIKE :gender
                    ELSE 1
                END
            ORDER BY id ASC
        """
    )
    fun getCharacters(
        filterByName: Boolean = false,
        name: String = "",
        filterByStatus: Boolean = false,
        status: String = "",
        filterByGender: Boolean = false,
        gender: String = ""
    ): PagingSource<Int, RMCharacterEntity>

    @Query(
        value = """
            SELECT * FROM character_table 
            WHERE 
                CASE WHEN :useFilterIds
                    THEN id IN (:filterIds)
                    ELSE 1
                END
            ORDER BY name ASC
        """
    )
    fun getCharacters(
        useFilterIds: Boolean = false,
        filterIds: Set<Int> = emptySet()
    ): Flow<List<RMCharacterEntity>>

    @Query(
        value = """
            SELECT * FROM character_table
            WHERE name LIKE :query
            ORDER BY name ASC
        """
    )
    suspend fun searchCharacter(query: String): List<RMCharacterEntity>

    @Query(
        value = """
            SELECT * FROM character_table 
            WHERE id Like :id
        """
    )
    fun getCharacter(id: Int): Flow<RMCharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: RMEpisodeEntity)

    @Query(
        value = """
            SELECT * FROM episode_table 
            WHERE id Like :id
        """
    )
    fun getEpisode(id: Int): Flow<RMEpisodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: RMLocationEntity)

    @Query(
        value = """
            SELECT * FROM location_table 
            WHERE id Like :id
        """
    )
    fun getLocation(id: Int): Flow<RMLocationEntity>

}