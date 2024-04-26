package com.example.daleee.ui.screen_fun

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.daleee.DP_VALUR_16
import com.example.daleee.DP_VALUR_40
import com.example.daleee.R
import com.example.daleee.ui.models.LinePath

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