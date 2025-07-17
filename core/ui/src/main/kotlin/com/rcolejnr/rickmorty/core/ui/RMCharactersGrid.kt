package com.rcolejnr.rickmorty.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rcolejnr.rickmorty.core.model.UserRMCharacter

@Composable
fun RMCharactersGrid(
    characters: List<UserRMCharacter>,
    onFavoriteClick: (Int) -> Unit,
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
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = state
        ) {
            if (characters.isNotEmpty()) {
                items(characters) { character ->
                    CharacterItem(
                        character,
                        onFavoriteClick = onFavoriteClick,
                        onCharacterClick = onCharacterClick
                    )
                }
            }
        }
    }
}
