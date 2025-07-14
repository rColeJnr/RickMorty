package com.rcolejnr.rickmorty.core.data.repository

import androidx.paging.PagingData
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import kotlinx.coroutines.flow.Flow

interface UserCharacterRepository {

    fun observeAll(
        name: String?,
        status: String?,
        genre: String?
    ): Flow<PagingData<UserRMCharacter>>

    fun observeCharacterFavorites(): Flow<List<UserRMCharacter>>

}