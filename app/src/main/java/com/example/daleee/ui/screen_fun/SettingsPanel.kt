package com.example.daleee.ui.screen_fun

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.daleee.DP_VALUR_0
import com.example.daleee.DP_VALUR_16
import com.example.daleee.DP_VALUR_40
import com.example.daleee.DP_VALUR_8
import com.example.daleee.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPanel(
    onClick: (Color) -> Unit,
    onWidthChanged: (Float) -> Unit,
    sheetState: SheetState,
    scope: CoroutineScope,
    onCanceledClick: () -> Unit,
    onCapChanged: (StrokeCap) -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.45f)
            .background(color = Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HideBottomSheet(sheetState, scope)
        ColorList { color ->
            onClick(color)
        }
        CustomSlider { lineWidth ->
            onWidthChanged(lineWidth)
        }
        ButtonPanel(onClick, onCanceledClick, onCapChanged, sheetState, scope)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DP_VALUR_16, vertical = DP_VALUR_8),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "ластик")
            Text(text = "форма кисти")
            Text(text = "отмена")
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
        modifier = Modifier.padding(DP_VALUR_16)
    ) {
        items(colors) { color ->
            Box(
                modifier = Modifier
                    .padding(end = DP_VALUR_8)
                    .clickable {
                        onClick(color)
                    }
                    .size(DP_VALUR_40)
                    .background(color = color, CircleShape)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HideBottomSheet(sheetState: SheetState, scope: CoroutineScope) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(end = DP_VALUR_16, bottom = DP_VALUR_16, top = DP_VALUR_8),
        Alignment.CenterEnd,
    ) {
        Button(
            onClick = {
                scope.launch {
                    sheetState.partialExpand()
                }
            },
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray),
            contentPadding = PaddingValues(DP_VALUR_0)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_down),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonPanel(
    onClickEraser: (Color) -> Unit,
    onCanceled: () -> Unit,
    onCapChanges: (StrokeCap) -> Unit,
    sheetState: SheetState,
    scope: CoroutineScope,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = DP_VALUR_16)
    ) {
        Button(
            onClick = {
                onClickEraser(ERASE_COLOR)
            },
            modifier = Modifier.size(DP_VALUR_40),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray),
            contentPadding = PaddingValues(DP_VALUR_0)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_eraser),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Button(
            onClick = {
                onCapChanges(StrokeCap.Round)
            },
            modifier = Modifier.size(DP_VALUR_40),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray),
            contentPadding = PaddingValues(DP_VALUR_0)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_circle),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Button(
            onClick = {
                onCapChanges(StrokeCap.Square)
            },
            modifier = Modifier.size(DP_VALUR_40),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray),
            contentPadding = PaddingValues(DP_VALUR_0)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_square),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Button(
            onClick = {
                onCapChanges(StrokeCap.Butt)
            },
            modifier = Modifier.size(DP_VALUR_40),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray),
            contentPadding = PaddingValues(DP_VALUR_0)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_butt),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Button(
            onClick = {
                onCanceled()
                scope.launch {
                    sheetState.partialExpand()
                    delay(DELAY_ALFA_SHEET)
                    sheetState.expand()
                }
            },
            modifier = Modifier
                .size(40.dp),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray),
            contentPadding = PaddingValues(DP_VALUR_0)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_rollback),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

private const val DELAY_ALFA_SHEET = 500L
val ERASE_COLOR = Color(0xFFFFFBFE)