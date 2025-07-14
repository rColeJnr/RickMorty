package com.rcolejnr.rickmorty.core.model

data class RMCharacter(
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
    val image: String,
    val episodes: List<String>,
    val characterUrl: String,
    val created: String
)