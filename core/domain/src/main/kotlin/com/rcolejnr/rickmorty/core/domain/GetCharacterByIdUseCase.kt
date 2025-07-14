package com.rcolejnr.rickmorty.core.domain

import com.rcolejnr.rickmorty.core.data.repository.CharacterRepository
import com.rcolejnr.rickmorty.core.data.repository.UserDataRepository
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

    operator fun invoke(id: Int): Flow<UserRMCharacter> =
        characterRepository.fetchCharacter(id)
            .mapToUserCharacter(userDataRepository.userData)
}

private fun Flow<RMCharacterEntity>.mapToUserCharacter(userDataStream: Flow<UserData>): Flow<UserRMCharacter> =
    combine(userDataStream) { character, userData ->
        UserRMCharacter(character.asRMCharacter(), userData)
    }