package com.rcolejnr.rickmorty.core.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.rcolejnr.rickmorty.core.database.model.asRMCharacter
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import com.rcolejnr.rickmorty.core.model.mapToUserRMCharacter
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
    override fun observeAll(
        name: String?,
        status: String?,
        genre: String?
    ): Flow<PagingData<UserRMCharacter>> =
        characterRepository.fetchCharacters(
            name, status, genre
        ).combine(userDataRepository.userData) { characters, userData ->
            characters.map { it.asRMCharacter().mapToUserRMCharacter(userData) }
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