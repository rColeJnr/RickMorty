package com.rcolejnr.rickmorty.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rcolejnr.rickmorty.core.model.RMCharacter

@Entity(tableName = "character_table")
data class RMCharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val originUrl: String,
    val location: String,
    val locationUrl: String,
    val episodes: List<String>,
    val image: String,
    val characterUrl: String,
    val created: String
)

fun RMCharacterEntity.asRMCharacter() = RMCharacter(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin,
    originUrl = originUrl,
    location = location,
    locationUrl = locationUrl,
    image = image,
    episodes = episodes,
    characterUrl = characterUrl,
    created = created,
)

fun List<RMCharacterEntity>.asRMCharacter(): List<RMCharacter> =
    map { it.asRMCharacter() }


fun RMCharacterEntity.asFtsEntity() = RMCharacterFtsEntity(
    characterId = id,
    name = name,
    species = species,
    origin = origin
)