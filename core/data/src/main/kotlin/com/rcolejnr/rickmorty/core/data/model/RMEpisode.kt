package com.rcolejnr.rickmorty.core.data.model

import com.rcolejnr.rickmorty.core.database.model.RMEpisodeEntity
import com.rcolejnr.rickmorty.core.network.model.RMEpisodeNetwork

fun RMEpisodeNetwork.asEntity() = RMEpisodeEntity(
    id = id,
    name = name,
    released = released,
    characters = characters,
    episode = episode
)