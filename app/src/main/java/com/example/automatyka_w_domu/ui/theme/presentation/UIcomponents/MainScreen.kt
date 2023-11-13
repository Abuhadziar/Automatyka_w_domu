package com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.automatyka_w_domu.R
import com.example.automatyka_w_domu.model.UiState
import com.example.automatyka_w_domu.ui.theme.AppViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("MissingPermission")
@Composable
fun MainScreen(
    onPlusButtonClicked: () -> Unit,
    iconImage: Painter,
    viewModel: AppViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val devType: String by viewModel.deviceType
    val connectedDevices = viewModel.toDeviceInfoList(devType = devType)

    Column(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
        ) {
            items(connectedDevices) { device ->
                Card(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
                    elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation))
                ) {
                    Row(
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = iconImage,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )

                        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))

                        Text(
                            text = device.deviceName ?: "Unknown Device",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = if (device.deviceStatus) {
                                "ON"
                            } else {
                                "OFF"
                            },
                            color = if (device.deviceStatus) {
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
                .padding(dimensionResource(R.dimen.padding_medium))
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

@Preview
@Composable
fun MainScreenPreview() {
    // Tutaj możesz dostosować parametry do swoich potrzeb
    MainScreen(
        onPlusButtonClicked = {},
        iconImage = painterResource(R.drawable.app_icon),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Opcjonalne tło
    )
}