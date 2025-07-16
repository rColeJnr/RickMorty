package com.rcolejnr.rickmorty.core.data.repository

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.rcolejnr.rickmorty.core.database.model.asRMCharacter
import com.rcolejnr.rickmorty.core.model.CharacterFilters
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import com.rcolejnr.rickmorty.core.model.mapToUserRMCharacter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class UserCharacterRepositoryImpl @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val userDataRepository: UserDataRepository
): UserCharacterRepository {
    override fun observeCharacters(
        scope: CoroutineScope,
        characterFilter: CharacterFilters
    ): Flow<PagingData<UserRMCharacter>> {
        return characterRepository.fetchCharacters(
            characterFilter
        ).cachedIn(scope)
            .combine(userDataRepository.userData) { characters, userData ->
                characters.map { it.asRMCharacter().mapToUserRMCharacter(userData) }
        }
    }

    override fun observeCharacterFavorites(): Flow<List<UserRMCharacter>> =
        userDataRepository.userData.map { it.characterFavorites }.distinctUntilChanged()
            .flatMapLatest { favoriteIds ->
                when {
                    favoriteIds.isEmpty() -> flowOf(emptyList())
                    else -> characterRepository.fetchCharacterFavorites(favoriteIds)
                        .combine(userDataRepository.userData) { characters, userData ->
                            characters.map { it.mapToUserRMCharacter(userData) }
                        }
                }
            }

}