package com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.automatyka_w_domu.R

@Composable
fun StartScreen(
    onStartButtonClicked: () -> Unit,
    iconImage: Painter,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = iconImage,
            contentDescription = "Start",
            modifier = modifier
                .size(dimensionResource(R.dimen.image_size))
                .clickable { onStartButtonClicked }
        )
        Button (
            onClick = onStartButtonClicked,
            modifier = modifier
                .widthIn(dimensionResource(R.dimen.size_large))
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(text = "Lets Start!")
        }
    }
}