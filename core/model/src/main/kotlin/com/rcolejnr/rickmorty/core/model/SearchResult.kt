package com.rcolejnr.rickmorty.core.model

data class SearchResult(
    val characters: List<RMCharacter> = emptyList()
)