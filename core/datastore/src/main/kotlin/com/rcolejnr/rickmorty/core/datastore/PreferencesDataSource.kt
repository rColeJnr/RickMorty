package com.rcolejnr.rickmorty.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.rcolejnr.rickmorty.core.model.CharacterFilters
import com.rcolejnr.rickmorty.core.model.DarkThemeConfig
import com.rcolejnr.rickmorty.core.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userData = userPreferences.data
        .map {
            UserData(
                characterFavorites = it.characterFavoriteIdsMap.keys,
                darkThemeConfig = when (it.darkThemeConfigProto) {
                    null,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                    DarkThemeConfigProto.UNRECOGNIZED,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM ->
                        DarkThemeConfig.FOLLOW_SYSTEM
                    DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
                    DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT -> DarkThemeConfig.LIGHT
                },
                characterFilter = when (it.characterFilterProto) {
                    null,
                    CharacterFilterProto.CHARACTER_FILTER_NULL,
                    CharacterFilterProto.UNRECOGNIZED -> CharacterFilters.NO_FILTER
                    CharacterFilterProto.CHARACTER_FILTER_RICK -> CharacterFilters.RICK
                    CharacterFilterProto.CHARACTER_FILTER_MORTY -> CharacterFilters.MORTY
                    CharacterFilterProto.CHARACTER_FILTER_DEAD -> CharacterFilters.DEAD
                    CharacterFilterProto.CHARACTER_FILTER_ALIVE -> CharacterFilters.ALIVE
                    CharacterFilterProto.CHARACTER_FILTER_MALE -> CharacterFilters.MALE
                    CharacterFilterProto.CHARACTER_FILTER_FEMALE -> CharacterFilters.FEMALE
                }
            )
        }

    suspend fun setFavoriteCharactersIds(characterId: Int, isFavorite: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    if (isFavorite) {
                        characterFavoriteIds.put(characterId, true)
                    } else {
                        characterFavoriteIds.remove(characterId)
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("rmappPreferences", "Failed to update user preferences.", ioException)
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData {
            it.copy {
                this.darkThemeConfigProto = when (darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM -> DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                }
            }
        }
    }

    suspend fun setCharacterFilter(characterFilter: CharacterFilters) {
        userPreferences.updateData {
            it.copy {
                this.characterFilterProto = when(characterFilter) {
                    CharacterFilters.NO_FILTER -> CharacterFilterProto.CHARACTER_FILTER_NULL
                    CharacterFilters.RICK -> CharacterFilterProto.CHARACTER_FILTER_RICK
                    CharacterFilters.MORTY -> CharacterFilterProto.CHARACTER_FILTER_MORTY
                    CharacterFilters.DEAD -> CharacterFilterProto.CHARACTER_FILTER_DEAD
                    CharacterFilters.ALIVE -> CharacterFilterProto.CHARACTER_FILTER_ALIVE
                    CharacterFilters.MALE -> CharacterFilterProto.CHARACTER_FILTER_MALE
                    CharacterFilters.FEMALE -> CharacterFilterProto.CHARACTER_FILTER_FEMALE
                }
            }
        }
    }
}