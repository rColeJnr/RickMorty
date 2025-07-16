package com.rcolejnr.rickmorty.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rcolejnr.rickmorty.core.model.UserRMCharacter


@Composable
fun CharacterItem(
    character: UserRMCharacter,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
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
            RMImageLoader(character.image, modifier = Modifier.fillMaxWidth().height(180.dp))
            RMLargeText(character.name)
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RMMediumText(character.gender)
                VerticalDivider(color = Color.Black, modifier = Modifier.fillMaxHeight().width(2.dp))
                RMMediumText(character.species)
            }
            RMMediumText(character.status)
        }
    }
}