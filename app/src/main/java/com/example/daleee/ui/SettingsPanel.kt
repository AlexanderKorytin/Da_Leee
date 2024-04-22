package com.example.daleee.ui

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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.daleee.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPanel(
    onClick: (Color) -> Unit,
    onWidthChanged: (Float) -> Unit,
    sheetState: SheetState,
    scope: CoroutineScope,
    onCanceledClick: () -> Unit,
    onCapChanged: (StrokeCap) -> Unit
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
        ButtonPanel(onClick, onWidthChanged, onCanceledClick, onCapChanged)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HideBottomSheet(sheetState: SheetState, scope: CoroutineScope) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(end = 16.dp, bottom = 16.dp, top = 8.dp),
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
            contentPadding = PaddingValues(0.dp)
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

@Composable
fun ButtonPanel(
    onClick: (Color) -> Unit,
    onWidthChanged: (Float) -> Unit,
    onCanceled: () -> Unit,
    onCapChages: (StrokeCap) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Button(
            onClick = {
                onClick(Color.White)
                onWidthChanged(80f)
            },
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray),
            contentPadding = PaddingValues(0.dp)
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
                onCapChages(StrokeCap.Round)
            },
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray),
            contentPadding = PaddingValues(0.dp)
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
                onCapChages(StrokeCap.Square)
            },
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray),
            contentPadding = PaddingValues(0.dp)
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
                onCapChages(StrokeCap.Butt)
            },
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray),
            contentPadding = PaddingValues(0.dp)
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
            },
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray),
            contentPadding = PaddingValues(0.dp)
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