package com.example.daleee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.example.daleee.ui.models.LinePath
import com.example.daleee.ui.screen_fun.MainScreen
import com.example.daleee.ui.theme.DaLeeeTheme

class MainActivity : ComponentActivity() {
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
                MainScreen()
            }
        }
    }
}

const val ANIM_DURATION_IN = 500
const val ANIM_DURATION_OUT = 500
val DP_VALUR_40 = 40.dp
val DP_VALUR_16 = 16.dp
val DP_VALUR_8 = 8.dp
val DP_VALUR_0 = 0.dp