package com.example.daleee.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SettingsPanel(onClick: (Color) -> Unit, onWidthChanged: (Float) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
            .background(color = Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColorList { color ->
            onClick(color)
        }
        CustomSlider { lineWidth ->
            onWidthChanged(lineWidth)
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
    Column {

    }
    LazyRow(
        modifier = Modifier.padding(16.dp)
    ) {
        items(colors) { color ->
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

@Composable
fun CustomSlider(onPositionChanged: (Float) -> Unit) {
    var position by remember {
        mutableStateOf(0.05f)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Толщина линии: ${(position * 100).toInt()}")
        Slider(
            value = position,
            onValueChange = {
                position = if (it > 0) it else 0.05f
                onPositionChanged(position * 100)
            },
            modifier = Modifier
                .background(color = Color.LightGray)
                .padding(8.dp)
        )

    }
}