package com.rcolejnr.rickmorty.feature.favorites

import com.rcolejnr.rickmorty.core.model.UserRMCharacter

sealed interface FavoritesUiState {

    data object Loading: FavoritesUiState

    data class Success(val characters: List<UserRMCharacter>) : FavoritesUiState {
        fun isEmpty() = characters.isEmpty()
    }

}