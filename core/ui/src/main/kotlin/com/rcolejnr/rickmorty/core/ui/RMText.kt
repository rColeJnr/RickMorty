package com.rcolejnr.rickmorty.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun RMMediumText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(text, style = MaterialTheme.typography.bodyMedium, modifier = modifier)
}

@Composable
fun RMHeadlineText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(text, style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center, modifier = modifier)
}

@Composable
fun RMLargeText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(text, style = MaterialTheme.typography.bodyLarge, modifier = modifier)
}
