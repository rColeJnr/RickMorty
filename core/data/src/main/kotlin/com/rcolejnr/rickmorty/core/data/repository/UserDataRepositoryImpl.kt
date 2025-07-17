package com.rcolejnr.rickmorty.core.data.repository

import com.rcolejnr.rickmorty.core.datastore.PreferencesDataSource
import com.rcolejnr.rickmorty.core.model.CharacterFilters
import com.rcolejnr.rickmorty.core.model.DarkThemeConfig
import com.rcolejnr.rickmorty.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class UserDataRepositoryImpl @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
): UserDataRepository{

    override val userData: Flow<UserData> =
        preferencesDataSource.userData

    override suspend fun setCharacterFavoriteId(characterId: Int, isFavorite: Boolean) {
        preferencesDataSource.setFavoriteCharactersIds(characterId, isFavorite)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        preferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setCharacterFilter(characterFilter: CharacterFilters) {
        preferencesDataSource.setCharacterFilter(characterFilter)
    }
}