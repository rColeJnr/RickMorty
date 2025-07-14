package com.rcolejnr.rickmorty.core.model

data class UserData(
    val characterFavorites: Set<Int>,
    val darkThemeConfig: DarkThemeConfig,
    val characterFilter: CharacterFilters
)
