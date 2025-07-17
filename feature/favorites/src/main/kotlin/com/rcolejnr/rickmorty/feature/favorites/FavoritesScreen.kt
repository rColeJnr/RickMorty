package com.rcolejnr.rickmorty.feature.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rcolejnr.rickmorty.core.ui.RMCharactersGrid
import com.rcolejnr.rickmorty.core.ui.RMHeadlineText

@Composable
fun FavoritesScreen(
    onBackClick: () -> Unit,
    onCharacterClick: (Int) -> Unit,
    favoriteViewModel: FavoritesViewModel
) {

    val favoritesUiState by favoriteViewModel.favorites.collectAsStateWithLifecycle()

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        FavoriteToolbar(
            onBackClick = onBackClick
        )
        HorizontalDivider(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
        when(favoritesUiState) {
            is FavoritesUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(80.dp),
                )
            }
            is FavoritesUiState.Success -> {
                if ((favoritesUiState as FavoritesUiState.Success).isEmpty()) {
                    RMHeadlineText(
                        "It's empty in here, \n tap some 🖤",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else {
                    val characters = (favoritesUiState as FavoritesUiState.Success).characters
                    RMCharactersGrid(
                        characters = characters,
                        onFavoriteClick = favoriteViewModel::removeCharacterFavorite,
                        onCharacterClick = onCharacterClick
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoriteToolbar(
    onBackClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }
        RMHeadlineText("Favorites")
    }
}