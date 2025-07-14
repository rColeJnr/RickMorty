package com.rcolejnr.rickmorty.core.network

import com.rcolejnr.rickmorty.core.network.model.RMCharacterNetwork
import com.rcolejnr.rickmorty.core.network.model.RMCharacterResponse
import com.rcolejnr.rickmorty.core.network.model.RMEpisodeNetwork
import com.rcolejnr.rickmorty.core.network.model.RMLocationNetwork

interface RMDataSourceNetwork {

    suspend fun fetchCharacters(
        page: Int,
        name: String?,
        status: String?,
        gender: String?,
    ): RMCharacterResponse

    suspend fun fetchCharacter(
        id: Int
    ): RMCharacterNetwork

    suspend fun fetchEpisode(
        id: Int
    ): RMEpisodeNetwork

    suspend fun fetchLocation(
        id: Int
    ): RMLocationNetwork
}