package com.rcolejnr.rickmorty.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RMErrorMsg(modifier: Modifier = Modifier, message: String, onClick: () -> Unit = {}) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        RMHeadlineText(
            text = "Something went wrong 😓 \n\n$message", modifier = modifier
        )
        TextButton(
            onClick = { onClick() },
        ) {
            RMLargeText(modifier = Modifier, text = "Click to retry")
        }
    }
}
