package com.rcolejnr.rickmorty.core.data.model

import com.rcolejnr.rickmorty.core.database.model.RMLocationEntity
import com.rcolejnr.rickmorty.core.network.model.RMLocationNetwork

fun RMLocationNetwork.asEntity() = RMLocationEntity(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    created = created
)