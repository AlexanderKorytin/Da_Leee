package com.example.daleee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.example.daleee.ui.LinePath
import com.example.daleee.ui.SettingsPanel
import com.example.daleee.ui.theme.DaLeeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val linePath = remember {
                mutableStateOf(LinePath())
            }
            DaLeeeTheme {
                Column {
                    SettingsPanel(
                        { color -> linePath.value = linePath.value.copy(color = color) },
                        { lineWidth -> linePath.value = linePath.value.copy(lineWidth = lineWidth)})
                    Greeting(linePath)
                }
            }
        }
    }
}

@Composable
fun Greeting(linePath: MutableState<LinePath>) {
    var currentPath = Path()
    val pathList = remember {
        mutableStateListOf(LinePath())
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = {
                        currentPath = Path()
                    },
                    onDragEnd = {
                        pathList.add(linePath.value.copy(path = currentPath))
                    }
                ) { change, dragAmount ->
                    currentPath.moveTo(
                        change.position.x - dragAmount.x,
                        change.position.y - dragAmount.y
                    )
                    currentPath.lineTo(
                        change.position.x,
                        change.position.y
                    )
                    if (pathList.size > 0) pathList.removeAt(pathList.lastIndex)
                    pathList.add(linePath.value.copy(path = currentPath))
                }
            }
    ) {
        pathList.forEach { line ->
            drawPath(
                line.path,
                color = line.color,
                style = Stroke(width = line.lineWidth)
            )
        }
    }
}

