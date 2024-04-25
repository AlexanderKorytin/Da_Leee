package com.example.daleee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.daleee.ui.LinePath
import com.example.daleee.ui.SettingsPanel
import com.example.daleee.ui.theme.DaLeeeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val linePath = remember {
                mutableStateOf(LinePath())
            }
            val pathList = remember {
                mutableStateListOf(LinePath())
            }
            DaLeeeTheme {
                val sheetState =
                    rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded)
                val scaffoldState = rememberBottomSheetScaffoldState(
                    sheetState
                )
                val scope = rememberCoroutineScope()
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetContent = {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            SettingsPanel(
                                { color -> linePath.value = linePath.value.copy(color = color) },
                                { lineWidth ->
                                    linePath.value = linePath.value.copy(lineWidth = lineWidth)
                                },
                                sheetState,
                                scope,
                                {
                                    pathList.removeIf { path ->
                                        pathList[pathList.size - 1] == path
                                    }
                                    if (pathList.isNotEmpty()) {
                                        pathList.add(pathList.last())
                                    }

                                },
                                { cap ->
                                    linePath.value = linePath.value.copy(currentCap = cap)
                                }
                            )
                        }
                    }, sheetContainerColor = Color.LightGray
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        DrawCanvas(linePath, pathList)
                    }
                }
            }
        }
    }
}

@Composable
fun DrawCanvas(linePath: MutableState<LinePath>, pathList: SnapshotStateList<LinePath>) {
    var currentPath = Path()
    Box {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
                .pointerInput(true) {
                    currentPath = Path()
                    detectTapGestures(onPress = {
                        currentPath = Path()
                        currentPath.moveTo(it.x, it.y)
                        currentPath.addOval(Rect(center = Offset(it.x, it.y), radius = 0f))
                        pathList.add(linePath.value.copy(path = currentPath))
                    })
                }
                .pointerInput(true) {
                    detectDragGestures(
                        onDrag = { change, _ ->
                            currentPath.lineTo(
                                change.position.x,
                                change.position.y
                            )
                            if (pathList.size > 0) {
                                pathList.removeAt(pathList.lastIndex)
                            }
                            pathList.add(linePath.value.copy(path = currentPath))
                        },
                        onDragEnd = {
                            pathList.add(linePath.value.copy(path = currentPath))
                        })
                }
        ) {
            pathList.forEach { line ->
                drawPath(
                    line.path,
                    color = line.color,
                    style = Stroke(width = line.lineWidth, cap = line.currentCap)
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(40.dp)
                .shadow(elevation = 16.dp, shape = CircleShape, ambientColor = linePath.value.color)
                .clipToBounds()
                .background(color = linePath.value.color, CircleShape)
                .align(Alignment.TopStart)
        )
    }
}

