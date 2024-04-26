package com.example.daleee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.dp
import com.example.daleee.ui.screen_fun.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

const val ANIM_DURATION_IN = 500
const val ANIM_DURATION_OUT = 500
val DP_VALUR_40 = 40.dp
val DP_VALUR_16 = 16.dp
val DP_VALUR_8 = 8.dp
val DP_VALUR_0 = 0.dp