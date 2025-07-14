package com.rcolejnr.rickmorty.core.database.model

import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "character_fts_table")
@Fts4
data class RMCharacterFtsEntity(
    val characterId: Int,
    val name: String,
    val species: String,
    val origin: String
)
