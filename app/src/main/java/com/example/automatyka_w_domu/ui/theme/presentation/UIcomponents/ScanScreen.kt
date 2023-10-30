package com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.automatyka_w_domu.BLE.BluetoothViewModel
import com.example.automatyka_w_domu.R
import java.util.UUID


@Composable
fun scanScreen(
    viewModel: BluetoothViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    context: Context,
    serviceUUID: UUID
) {
    Column(modifier = modifier) {
        deviceList(
            viewModel = viewModel,
            modifier = Modifier.weight(1f),
            context = context
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.startScanning(context, serviceUUID) },
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
    context: Context,
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
                DeviceCard(device = device) { address ->
                    viewModel.connectToDevice(address, context)
                }
            }
        }
        Text(
            text = "Connected devices:",
            fontWeight = FontWeight.Bold
        )
        LazyColumn {
            items(connectedDevices) {device ->
                DeviceCard(device = device) { address ->
                    viewModel.connectToDevice(address, context)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun DeviceCard(
    device: BluetoothDevice,
    onDeviceClicked: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = { onDeviceClicked(device.address) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = device.name ?: "Unknown",
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Address: ${device.address}",
                fontWeight = FontWeight.Medium
            )
        }
    }
}