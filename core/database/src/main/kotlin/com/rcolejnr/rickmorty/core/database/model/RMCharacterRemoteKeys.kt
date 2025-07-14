package com.rcolejnr.rickmorty.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rm_character_remote_keys")
class RMCharacterRemoteKeys (
    @PrimaryKey
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)