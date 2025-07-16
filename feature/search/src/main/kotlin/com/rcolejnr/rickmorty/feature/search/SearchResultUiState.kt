package com.rcolejnr.rickmorty.feature.search

import com.rcolejnr.rickmorty.core.model.UserRMCharacter

sealed interface SearchResultUiState {

    data object Loading: SearchResultUiState

    data object EmptyQuery: SearchResultUiState

    data object LoadFailed: SearchResultUiState

    data class Success(
        val characters: List<UserRMCharacter> = emptyList()
    ): SearchResultUiState {
        fun isEmpty(): Boolean = characters.isEmpty()
    }

    /**
     * A state where the search contents are not ready. This happens when the *Fts tables are not
     * populated yet.
     */
    data object SearchNotReady : SearchResultUiState
}