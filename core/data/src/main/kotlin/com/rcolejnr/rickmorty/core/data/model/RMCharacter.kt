package com.rcolejnr.rickmorty.core.data.model

import com.rcolejnr.rickmorty.core.database.model.RMCharacterEntity
import com.rcolejnr.rickmorty.core.network.model.RMCharacterNetwork

fun RMCharacterNetwork.asEntity() = RMCharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.name,
    originUrl = origin.locationUrl,
    location = location.name,
    locationUrl = location.url,
    episodes = episodes,
    image = image,
    characterUrl = characterUrl,
    created = created
)