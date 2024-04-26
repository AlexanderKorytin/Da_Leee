package com.example.daleee.ui.screen_fun

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.daleee.ANIM_DURATION_IN
import com.example.daleee.ANIM_DURATION_OUT
import com.example.daleee.DP_VALUR_40
import com.example.daleee.DP_VALUR_8

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
                .padding(DP_VALUR_8)
        )
    }
}