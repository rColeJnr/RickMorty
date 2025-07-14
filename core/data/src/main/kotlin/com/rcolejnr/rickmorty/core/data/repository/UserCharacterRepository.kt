package com.rcolejnr.rickmorty.core.data.repository

import androidx.paging.PagingData
import com.rcolejnr.rickmorty.core.model.CharacterFilters
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface UserCharacterRepository {

    fun observeCharacters(
        scope: CoroutineScope,
        characterFilter: CharacterFilters
    ): Flow<PagingData<UserRMCharacter>>

    fun observeCharacterFavorites(): Flow<List<UserRMCharacter>>

}