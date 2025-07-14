package com.rcolejnr.rickmorty.core.model

data class RMEpisode(
    val id: Int,
    val name: String,
    val released: String,
    val episode: String,
    val characters: List<String>
)
