package com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.automatyka_w_domu.R
import com.example.automatyka_w_domu.ui.theme.AppViewModel
import androidx.compose.ui.res.painterResource
import com.example.automatyka_w_domu.BLE.BluetoothViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun MainScreen(
    onPlusButtonClicked: () -> Unit,
    onDeviceClicked: (type: String) -> Unit,
    bluetoothViewModel: BluetoothViewModel,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val connectedDevices = bluetoothViewModel.getConnectedDevices()
    val status: Boolean = true

    Column(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(connectedDevices) { device ->
                Card(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
                    elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation)),
                    onClick = {
                        onDeviceClicked(device.type)
                        bluetoothViewModel.selectedConnectedDevice.value = device
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(viewModel.mainScreenIcon(device.type)),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))

                        Text(
                            text = device.result.device.name ?: "Unknown Device",
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center
        ) {
            FloatingActionButton(
                onClick = { onPlusButtonClicked() },
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}