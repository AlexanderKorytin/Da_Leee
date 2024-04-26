package com.example.daleee.ui.screen_fun

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import com.example.daleee.DP_VALUR_16
import com.example.daleee.ui.models.LinePath
import com.example.daleee.ui.theme.DaLeeeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
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
        val imageUri = remember {
            mutableStateOf<Uri?>(null)
        }
        val isAlfaSliderVisibility = remember {
            mutableStateOf(false)
        }
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
                DrawCanvas(linePath, pathList, imageUri, isAlfaSliderVisibility)
                Box(
                    modifier = Modifier
                        .padding(DP_VALUR_16)
                        .wrapContentSize()
                        .clipToBounds()
                        .align(Alignment.TopEnd)
                ) {
                    BackGroundImage(imageUri)
                }
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clipToBounds()
                        .align(Alignment.TopStart)
                        .rotate(-90f)
                        .padding(DP_VALUR_16), contentAlignment = Alignment.Center
                ) {
                    AlfaSlider({ currentAlfa ->
                        linePath.value = linePath.value.copy(alfa = currentAlfa)
                    }, isAlfaSliderVisibility, linePath.value.color)
                }
            }
        }
    }
}