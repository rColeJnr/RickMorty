package com.rcolejnr.rickmorty.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import com.rcolejnr.rickmorty.core.ui.CharacterItem

@Composable
internal fun SearchScreen(
    onBackClick: () -> Unit,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel
) {

    val recentSearchQueriesUiState by searchViewModel.rsqUiState.collectAsStateWithLifecycle()
    val searchResultUiState by searchViewModel.searchResultUiState.collectAsStateWithLifecycle()
    val searchQuery by searchViewModel.searchQuery.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {

        SearchToolbar(
            onBackClick = onBackClick,
            searchQuery = searchQuery,
            onSearchQueryChanged = searchViewModel::onSearchQueryChanged,
            onSearchTriggered = searchViewModel::onSearchTriggered
        )
        when (searchResultUiState) {
            SearchResultUiState.Loading, SearchResultUiState.LoadFailed, SearchResultUiState.SearchNotReady -> Unit
            SearchResultUiState.EmptyQuery,
                -> {
                if (recentSearchQueriesUiState is RecentSearchQueriesUiState.Success) {
                    RecentSearchesBody(
                        onClearRsq = searchViewModel::clearRecentSearches,
                        onRsqClick = searchViewModel::onSearchTriggered,
                        rsq = (recentSearchQueriesUiState as RecentSearchQueriesUiState.Success).rsq.map { it.query },
                    )
                }
            }

            is SearchResultUiState.Success -> {
                if ((searchResultUiState as SearchResultUiState.Success).isEmpty()) {
                    EmptySearchResultBody(
                        searchQuery = searchQuery,
                    )
                    if (recentSearchQueriesUiState is RecentSearchQueriesUiState.Success) {
                        RecentSearchesBody(
                            onClearRsq = searchViewModel::clearRecentSearches,
                            onRsqClick = {
                                searchViewModel.onSearchQueryChanged(it)
                                searchViewModel.onSearchTriggered(it)
                            },
                            rsq = (recentSearchQueriesUiState as RecentSearchQueriesUiState.Success).rsq.map { it.query },
                        )
                    }
                } else {
                    SearchResultBody(
                        characters = (searchResultUiState as SearchResultUiState.Success).characters,
                        onCharacterClick = { onCharacterClick(it) },
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchToolbar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
            )
        }
        SearchTextField(
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered,
            searchQuery = searchQuery,
        )
    }
}
@Composable
private fun SearchTextField(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyCalled = {
        keyboardController?.hide()
        onSearchTriggered(searchQuery)
    }

    TextField(
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onSearchQueryChanged("")
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null
                    )
                }
            }
        },
        onValueChange = { if ("\n" !in it) onSearchQueryChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    if (searchQuery.isBlank()) return@onKeyEvent false
                    onSearchExplicitlyCalled()
                    true
                } else {
                    false
                }
            },
        shape = RoundedCornerShape(32.dp),
        value = searchQuery,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if (searchQuery.isBlank()) return@KeyboardActions
                onSearchExplicitlyCalled()
            },
        ),
        maxLines = 1,
        singleLine = true,
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
private fun RecentSearchesBody(
    rsq: List<String>,
    onClearRsq: () -> Unit,
    onRsqClick: (String) -> Unit
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Recent searches... ")
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
            if (rsq.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onClearRsq()
                    },
                    modifier = Modifier.padding(horizontal = 16.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null
                    )
                }
            }
        }
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(rsq) { recentSearch ->
                Text(
                    text = recentSearch,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clickable { onRsqClick(recentSearch) }
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun SearchResultBody(
    characters: List<UserRMCharacter>,
    onCharacterClick: (Int) -> Unit
) {
    val state = rememberLazyGridState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = state
        ) {
            if (characters.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Characters")
                            }
                        },
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }

                items(characters) { character ->
                    CharacterItem(
                        character,
                        onCharacterClick = onCharacterClick
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptySearchResultBody(
    searchQuery: String
) {
    Text("No results found for $searchQuery", style = MaterialTheme.typography.headlineMedium)
}