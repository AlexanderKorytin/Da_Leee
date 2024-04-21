package com.example.daleee.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsPanel(onClick: (Color) -> Unit) {
    Column(
        Modifier.fillMaxWidth()
            .background(color = Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColorList(){color ->
            onClick(color)
        }
    }
}

@Composable
fun ColorList(onClick: (Color) -> Unit) {
    val colors = listOf(
        Color.Black,
        Color.Blue,
        Color.Cyan,
        Color.Red,
        Color.Green,
        Color.Magenta,
        Color.Yellow
    )

    LazyRow(
        modifier = Modifier.padding(16.dp)
    ) {
       items(colors){color ->
           Box(
               modifier = Modifier
                   .padding(end = 8.dp)
                   .clickable {
                       onClick(color)
                   }
                   .size(40.dp)
                   .background(color = color, CircleShape)
           )
       }
    }
}