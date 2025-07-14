package com.rcolejnr.rickmorty.core.domain

import com.rcolejnr.rickmorty.core.data.repository.CharacterRepository
import com.rcolejnr.rickmorty.core.database.model.asRMLocation
import com.rcolejnr.rickmorty.core.model.RMLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLocationByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
){
    operator fun invoke(id: Int): Flow<RMLocation> =
        characterRepository.fetchLocation(id)
            .map { it.asRMLocation() }
}