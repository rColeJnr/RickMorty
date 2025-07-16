package com.rcolejnr.rickmorty.feature.search

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import com.rcolejnr.rickmorty.feature.search.R.drawable

@Composable
internal fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val recentSearchQueriesUiState by searchViewModel.rsqUiState.collectAsStateWithLifecycle()
    val searchResultUiState by searchViewModel.searchResultUiState.collectAsStateWithLifecycle()
    val searchQuery by searchViewModel.searchQuery.collectAsStateWithLifecycle()
    val context = LocalContext.current
    Column(modifier = modifier.fillMaxSize()) {

        SearchTextField(
            searchQuery = searchQuery,
            onSearchQueryChanged = searchViewModel::onSearchQueryChanged,
            onSearchTriggered = searchViewModel::onSearchTriggered
        )
        when (searchResultUiState) {
        SearchResultUiState.Loading, SearchResultUiState.LoadFailed, SearchResultUiState.SearchNotReady -> Unit
        SearchResultUiState.EmptyQuery,
            -> {
            if (recentSearchQueriesUiState is RecentSearchQueriesUiState.Success) {
                RecentSearchesBody (
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
                    onCharacterClick = {
                        Toast.makeText(context, "hello $it", Toast.LENGTH_SHORT).show()
                    },
                )
            }
        }
    }

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
        onValueChange = {if ("\n" !in it) onSearchQueryChanged(it)},
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
                item (span = { GridItemSpan(2) }){
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

                items(characters){ character ->
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
private fun CharacterItem(
    character: UserRMCharacter,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier) {

    Card (
        shape = RoundedCornerShape(16.dp),
        onClick = { onCharacterClick(character.id) },
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CharacterImage(character.image)
            CharacterText(character.name)
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                CharacterText(character.gender)
                VerticalDivider(color = Color.Gray)
                CharacterText(character.species)
            }
            CharacterText(character.status)
        }
    }
}

@Composable
private fun CharacterText(
    text: String
) {
    Text(text, style = MaterialTheme.typography.bodyMedium)
}

@Composable
private fun CharacterImage(
    imageUrl: String?,
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = imageUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )
    val isLocalInspection = LocalInspectionMode.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            // Display a progress bar while loading
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(80.dp),
            )
        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Crop,
            painter = if (isError.not() && !isLocalInspection) {
                imageLoader
            } else {
                painterResource(drawable.rick_sanchez)
            },
            contentDescription = null,
        )
    }
}

@Composable
private fun EmptySearchResultBody (
    searchQuery: String
) {
    Text("No results found for $searchQuery", style = MaterialTheme.typography.headlineMedium)
}