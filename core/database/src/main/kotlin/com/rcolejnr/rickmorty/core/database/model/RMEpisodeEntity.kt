package com.rcolejnr.rickmorty.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rcolejnr.rickmorty.core.model.RMEpisode

@Entity(tableName = "episode_table")
data class RMEpisodeEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val released: String,
    val episode: String,
    val characters: List<String>,
)

fun RMEpisodeEntity.asRMEpisode() = RMEpisode(
    id, name, released, episode, characters
)