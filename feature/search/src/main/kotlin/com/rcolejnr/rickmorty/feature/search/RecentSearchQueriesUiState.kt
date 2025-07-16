package com.rcolejnr.rickmorty.feature.search

import com.rcolejnr.rickmorty.core.data.model.RecentSearchQuery

sealed interface RecentSearchQueriesUiState {

    data object Loading: RecentSearchQueriesUiState

    data class Success(val rsq: List<RecentSearchQuery> = emptyList()): RecentSearchQueriesUiState
}