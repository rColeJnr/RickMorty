package com.rcolejnr.rickmorty.feature.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rcolejnr.rickmorty.core.model.UserRMCharacter
import com.rcolejnr.rickmorty.core.ui.RMErrorMsg
import com.rcolejnr.rickmorty.core.ui.RMHeadlineText
import com.rcolejnr.rickmorty.core.ui.RMImageLoader
import com.rcolejnr.rickmorty.core.ui.RMLargeText

@Composable
fun CharacterScreen(
    onRetry: () -> Unit,
    onBackClick: () -> Unit,
    characterViewModel: CharacterViewModel,
) {

    val characterUiState by characterViewModel.character.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        when(characterUiState) {
            is CharacterUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(80.dp),
                )
            }
            is CharacterUiState.LoadError -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    RMErrorMsg(
                        modifier = Modifier.fillMaxWidth(),
                        (characterUiState as CharacterUiState.LoadError).error,
                        onClick = onRetry
                    )
                }
            }

            is CharacterUiState.Success -> {
                val character = (characterUiState as CharacterUiState.Success).character
                CharacterScreen(
                    onBackClick = onBackClick,
                    onFavoriteClick = characterViewModel::updateCharacterFavorite,
                    character = character
                )
            }
        }
    }
}

@Composable
private fun CharacterScreen(
    onBackClick: () -> Unit,
    onFavoriteClick: (Int, Boolean) -> Unit,
    character: UserRMCharacter,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
            RMHeadlineText(character.name)
            IconButton(onClick = { onFavoriteClick(character.id, character.isFavorite) }) {
                if (character.isFavorite)
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = null, tint = Color.Red)
                else
                    Icon(imageVector = Icons.Outlined.Favorite, contentDescription = null, tint = Color.LightGray)
            }
        }
        HorizontalDivider(Modifier
            .fillMaxWidth()
            .height(2.dp))
        RMImageLoader(
            character.image,
            modifier = Modifier
                .fillMaxWidth()
        )
        Card (
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors().copy(containerColor = Color.DarkGray, contentColor = Color.White),
            modifier = Modifier.fillMaxSize()
        ) {
            Column (
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                RMLargeText("Species: ${character.species}")
                RMLargeText("Type: ${character.type}")
                RMLargeText("Status: ${character.status}")
                RMLargeText("Gender: ${character.gender}")
                RMLargeText("Origin: ${character.origin}")
                RMLargeText("Location: ${character.location}")
            }
        }
    }
}