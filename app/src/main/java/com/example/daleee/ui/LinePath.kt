package com.example.daleee.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

data class LinePath(
    val color: Color = Color.Black,
    val path: Path = Path(),
    val lineWidth: Float = 5f
)
