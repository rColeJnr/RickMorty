package com.rcolejnr.rickmorty.core.domain

import com.rcolejnr.rickmorty.core.data.repository.CharacterRepository
import com.rcolejnr.rickmorty.core.data.util.FetchResult
import com.rcolejnr.rickmorty.core.database.model.RMLocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLocationByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
){
    operator fun invoke(id: Int): Flow<FetchResult<RMLocationEntity>> =
        characterRepository.fetchLocation(id)
            .map {result ->
                when (result) {
                    is FetchResult.Failure -> {
                        FetchResult.Failure(result.error)
                    }
                    is FetchResult.Success -> {
                        FetchResult.Success(result.value)
                    }
                }
            }
}