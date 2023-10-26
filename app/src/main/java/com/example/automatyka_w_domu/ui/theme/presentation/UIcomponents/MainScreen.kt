package com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.automatyka_w_domu.model.UiState

@SuppressLint("MissingPermission")
@Composable
fun MainScreen(
    onPlusButtonClicked: () -> Unit,
    uiState: UiState,
    iconImage: Painter,
    modifier: Modifier = Modifier
) {
    val status = uiState.deviceStatus
    val connectedDevices = uiState.connectedDevices

    Column(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
        ) {
            items(connectedDevices) { device ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = iconImage,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = device.name ?: "Unknown Device",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = if (status) {
                                "ON"
                            } else {
                                "OFF"
                            },
                            color = if (status) {
                                Color.Green
                            } else {
                                Color.Red
                            },
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onPlusButtonClicked,
            modifier = Modifier
                .padding(16.dp)
                .size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}