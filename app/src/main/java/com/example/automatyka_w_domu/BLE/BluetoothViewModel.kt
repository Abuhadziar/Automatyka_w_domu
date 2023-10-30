package com.example.automatyka_w_domu.BLE

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor() : ViewModel() {
    var scannedDevices by mutableStateOf<List<BluetoothDevice>>(emptyList())
        private set
    var connectedDevices by mutableStateOf<List<BluetoothDevice>>(emptyList())
        private set

    fun addScannedDevice(device: BluetoothDevice) {
        if (!scannedDevices.contains(device)) {
            scannedDevices += device
        }
    }

    fun addConnectedDevice(device: BluetoothDevice) {
        if (!connectedDevices.contains(device)) {
            connectedDevices += device
        }
    }

    fun startScanning(
        context: Context,
        serviceUUID: UUID
    ) {
        val bluetoothController = BluetoothController(context, BluetoothViewModel())
        bluetoothController.scanForDevices(context, serviceUUID)
    }

    fun connectToDevice(address: String, context: Context) {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        val device = adapter.getRemoteDevice(address)
        val bluetoothController = BluetoothController(context, BluetoothViewModel())
        bluetoothController.deviceFound(device)
    }
}