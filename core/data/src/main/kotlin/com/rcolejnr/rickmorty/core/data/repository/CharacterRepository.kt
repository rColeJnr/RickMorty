package com.rcolejnr.rickmorty.core.data.repository

import androidx.paging.PagingData
import com.rcolejnr.rickmorty.core.database.model.RMCharacterEntity
import com.rcolejnr.rickmorty.core.database.model.RMEpisodeEntity
import com.rcolejnr.rickmorty.core.database.model.RMLocationEntity
import com.rcolejnr.rickmorty.core.model.CharacterFilters
import com.rcolejnr.rickmorty.core.model.RMCharacter
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun fetchCharacters(
        characterFilters: CharacterFilters
    ): Flow<PagingData<RMCharacterEntity>>

    fun fetchCharacterFavorites(
        ids: Set<Int>
    ): Flow<List<RMCharacter>>

    fun fetchCharacter(
        id: Int
    ): Flow<RMCharacterEntity>

    fun fetchEpisode(
        id: Int
    ): Flow<RMEpisodeEntity>

    fun fetchLocation(
        id: Int
    ): Flow<RMLocationEntity>

}