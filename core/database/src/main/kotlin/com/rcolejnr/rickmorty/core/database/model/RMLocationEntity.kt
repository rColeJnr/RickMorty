package com.rcolejnr.rickmorty.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rcolejnr.rickmorty.core.model.RMLocation

@Entity("location_table")
data class RMLocationEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val created: String
)

fun RMLocationEntity.asRMLocation() = RMLocation(
    id, name, type, dimension, residents, created
)
