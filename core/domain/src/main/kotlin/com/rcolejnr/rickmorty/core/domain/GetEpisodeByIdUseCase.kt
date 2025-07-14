package com.rcolejnr.rickmorty.core.domain

import com.rcolejnr.rickmorty.core.data.repository.CharacterRepository
import com.rcolejnr.rickmorty.core.database.model.asRMEpisode
import com.rcolejnr.rickmorty.core.model.RMEpisode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetEpisodeByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
){
    operator fun invoke(id: Int): Flow<RMEpisode> =
        characterRepository.fetchEpisode(id)
            .map { it.asRMEpisode() }
}
