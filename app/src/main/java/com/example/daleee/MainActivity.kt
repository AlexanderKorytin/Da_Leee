package com.example.daleee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.example.daleee.ui.theme.DaLeeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DaLeeeTheme {
                Greeting()
            }
        }
    }
}

@Composable
fun Greeting() {

    val currentPath = Path()

    val path = remember {
        mutableStateOf(Path())
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectDragGestures { change, dragAmount ->
                    currentPath.moveTo(
                        change.position.x - dragAmount.x,
                        change.position.y - dragAmount.y
                    )
                    currentPath.lineTo(
                        change.position.x,
                        change.position.y
                    )
                    path.value = Path().apply {
                        addPath(currentPath)
                    }
                }
            }
    ) {
        drawPath(
            path.value,
            color = Color.Black,
            style = Stroke(width = 5.0f)
        )
    }
}

