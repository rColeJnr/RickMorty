package com.rcolejnr.rickmorty.feature.character

import com.rcolejnr.rickmorty.core.model.UserRMCharacter

sealed interface CharacterUiState {

    data object Loading: CharacterUiState

    data class Success(val character: UserRMCharacter): CharacterUiState

    data class LoadError(val error: String): CharacterUiState
}