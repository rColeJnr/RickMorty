package com.rcolejnr.rickmorty.core.data.repository

import com.rcolejnr.rickmorty.core.model.CharacterFilters
import com.rcolejnr.rickmorty.core.model.DarkThemeConfig
import com.rcolejnr.rickmorty.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userData: Flow<UserData>

    suspend fun setCharacterFavoriteId(characterId: Int, isFavorite: Boolean)

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    suspend fun setCharacterFilter(characterFilter: CharacterFilters)

}