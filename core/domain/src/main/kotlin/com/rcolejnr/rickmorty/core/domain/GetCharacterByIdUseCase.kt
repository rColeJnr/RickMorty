package com.rcolejnr.rickmorty.core.domain

import com.rcolejnr.rickmorty.core.data.repository.CharacterRepository
import com.rcolejnr.rickmorty.core.data.repository.UserDataRepository
import com.rcolejnr.rickmorty.core.data.util.FetchResult
import com.rcolejnr.rickmorty.core.database.model.RMCharacterEntity
import com.rcolejnr.rickmorty.core.database.model.asRMCharacter
import com.rcolejnr.rickmorty.core.model.UserData
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val userDataRepository: UserDataRepository
){

    operator fun invoke(id: Int): Flow<FetchResult<UserRMCharacter>> =
        characterRepository.fetchCharacter(id)
            .mapToUserCharacter(userDataRepository.userData)
}

private fun Flow<FetchResult<RMCharacterEntity>>.mapToUserCharacter(userDataStream: Flow<UserData>): Flow<FetchResult<UserRMCharacter>> =
    combine(userDataStream) { result, userData ->
        when (result) {
            is FetchResult.Success -> {
                val character = result.value.asRMCharacter()
                FetchResult.Success(UserRMCharacter(character, userData))
            }
            is FetchResult.Failure -> {
                FetchResult.Failure(result.error)
            }
        }
    }