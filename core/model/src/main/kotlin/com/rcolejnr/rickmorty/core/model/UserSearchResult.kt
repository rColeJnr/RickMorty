package com.rcolejnr.rickmorty.core.model

data class UserSearchResult(
    val characters: List<UserRMCharacter> = emptyList()
)
