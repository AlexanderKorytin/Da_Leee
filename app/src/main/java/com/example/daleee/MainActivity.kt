package com.example.daleee

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.daleee.ui.ERASE_COLOR
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
                            AddImage(imageUri)
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
    }
}

@Composable
fun AddImage(imageUri: MutableState<Uri?>) {
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }
    Column {
        Button(
            onClick = {
                launcher.launch("image/*")
            },
            modifier = Modifier
                .size(DP_VALUR_40)
                .align(Alignment.CenterHorizontally),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray),
            contentPadding = PaddingValues(DP_VALUR_0)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(text = "Add image", fontSize = 12.sp)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DrawCanvas(
    linePath: MutableState<LinePath>,
    pathList: SnapshotStateList<LinePath>,
    imageUri: MutableState<Uri?>,
    isAlfaSliderVisibility: MutableState<Boolean>,
) {
    var currentPath = Path()
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            GlideImage(
                model = imageUri.value,
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            ) {
                pathList.clear()
                it.load(imageUri.value)
            }
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
                .pointerInput(true) {
                    currentPath = Path()
                    detectTapGestures(onPress = {
                        isAlfaSliderVisibility.value = false
                        currentPath = Path()
                        currentPath.moveTo(it.x, it.y)
                        currentPath.addOval(Rect(center = Offset(it.x, it.y), radius = 1f))
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
                    style = Stroke(
                        width = line.lineWidth,
                        cap = line.currentCap,
                    ),
                    alpha = line.alfa
                )
            }
        }
        SetEraser(isAlfaSliderVisibility = isAlfaSliderVisibility, linePath = linePath)
        Box(modifier = Modifier
            .padding(DP_VALUR_16)
            .size(DP_VALUR_40)
            .clickable {
                pathList.clear()
                imageUri.value = null
            }
            .align(Alignment.TopCenter)) {
            Image(
                painter = painterResource(id = R.drawable.ic_trash_cart),
                contentDescription = null
            )
        }
    }
}

@Composable
fun SetEraser(isAlfaSliderVisibility: MutableState<Boolean>, linePath: MutableState<LinePath>) {
    AnimatedVisibility(
        visible = !isAlfaSliderVisibility.value,
        enter = fadeIn(animationSpec = tween(durationMillis = ANIM_DURATION_IN)),
        exit = fadeOut(animationSpec = tween(durationMillis = ANIM_DURATION_OUT))
    ) {
        Box(Modifier.pointerInput(true) {
            detectTapGestures(
                onPress = {
                    isAlfaSliderVisibility.value = true
                }
            )
        }) {
            if (linePath.value.color == ERASE_COLOR) {
                Image(
                    painterResource(id = R.drawable.ic_eraser), null,
                    modifier = Modifier
                        .padding(DP_VALUR_16)
                        .size(DP_VALUR_40)
                        .clipToBounds()
                        .align(Alignment.TopStart),
                )
            } else {
                Box(
                    modifier = Modifier
                        .padding(DP_VALUR_16)
                        .size(DP_VALUR_40)
                        .shadow(
                            elevation = DP_VALUR_16,
                            shape = CircleShape,
                            ambientColor = linePath.value.color
                        )
                        .clipToBounds()
                        .background(color = linePath.value.color)
                        .align(Alignment.TopStart),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlfaSlider(
    onAlfaChanged: (Float) -> Unit,
    isAlfaSliderVisibility: MutableState<Boolean>,
    color: Color
) {
    var position by remember {
        mutableFloatStateOf(1f)
    }
    AnimatedVisibility(
        visible = isAlfaSliderVisibility.value,
        enter = fadeIn(animationSpec = tween(durationMillis = ANIM_DURATION_IN)),
        exit = fadeOut(animationSpec = tween(durationMillis = ANIM_DURATION_OUT))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .size(300.dp)
        ) {
            Slider(
                value = position,
                onValueChange = {
                    position = if (it > 0) it else 0.05f
                    onAlfaChanged(position)
                },
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .height(DP_VALUR_40),
                onValueChangeFinished = {
                    isAlfaSliderVisibility.value = false
                },
                thumb = {
                    SliderDefaults.Thumb(
                        interactionSource = MutableInteractionSource(),
                        thumbSize = DpSize(DP_VALUR_40, DP_VALUR_40),
                        colors = SliderDefaults.colors(thumbColor = color)
                    )
                }
            )
            Text(text = "Толщина линии: ${(position * 100).toInt()}")
        }
    }
}

private const val ANIM_DURATION_IN = 500
private const val ANIM_DURATION_OUT = 500
val DP_VALUR_40 = 40.dp
val DP_VALUR_16 = 16.dp
val DP_VALUR_8 = 8.dp
val DP_VALUR_0 = 0.dp