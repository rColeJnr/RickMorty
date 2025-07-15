package com.rcolejnr.rickmorty.feature.favorites

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {
    Text("Favorites Screen", style = MaterialTheme.typography.headlineMedium)
}