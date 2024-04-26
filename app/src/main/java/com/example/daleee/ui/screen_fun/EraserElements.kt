package com.example.daleee.ui.screen_fun

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import com.example.daleee.ANIM_DURATION_IN
import com.example.daleee.ANIM_DURATION_OUT
import com.example.daleee.DP_VALUR_16
import com.example.daleee.DP_VALUR_40
import com.example.daleee.R
import com.example.daleee.ui.models.LinePath

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