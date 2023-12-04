package com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.automatyka_w_domu.R

@Composable
fun StartScreen(
    onStartButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.app_icon),
            contentDescription = "Start",
            modifier = modifier
                .size(364.dp)
                .clickable { onStartButtonClicked() }
                .clip(CircleShape),
            tint = Color.Unspecified
        )
        Button (
            onClick = { onStartButtonClicked() },
            modifier = modifier
                .widthIn(dimensionResource(R.dimen.size_large))
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(text = "Lets Start!")
        }
    }
}