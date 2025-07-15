package com.rcolejnr.rickmorty.feature.character

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CharacterScreen(modifier: Modifier = Modifier) {
    Text("Character Screen", style = MaterialTheme.typography.headlineMedium)
}