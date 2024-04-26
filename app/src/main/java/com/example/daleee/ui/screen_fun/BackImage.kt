package com.example.daleee.ui.screen_fun

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.daleee.DP_VALUR_0
import com.example.daleee.DP_VALUR_40
import com.example.daleee.R

@Composable
fun BackGroundImage(imageUri: MutableState<Uri?>) {
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