package com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.automatyka_w_domu.BLE.BluetoothViewModel
import com.example.automatyka_w_domu.R


@Composable
fun scanScreen(
    viewModel: BluetoothViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        deviceList(
            viewModel = viewModel,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.scan_button))
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.Done_button))
            }
        }
    }

}

@SuppressLint("MissingPermission")
@Composable
fun deviceList(
    viewModel: BluetoothViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val scannedDevices = viewModel.scannedDevices
    val connectedDevices = viewModel.connectedDevices
    
    Column(modifier = modifier) {
        Text(
            text = "Scanned devices:",
            fontWeight = FontWeight.Bold
        )
        LazyColumn {
            items(scannedDevices) {device ->
                Text(
                    text = device.name ?: "Unknown",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Address: ${device.address}",
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Text(
            text = "Connected devices:",
            fontWeight = FontWeight.Bold
        )
        LazyColumn {
            items(connectedDevices) {device ->
                Text(
                    text = device.name ?: "Unknown",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Address: ${device.address}",
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}