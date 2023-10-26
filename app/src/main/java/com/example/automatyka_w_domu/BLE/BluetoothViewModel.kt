package com.example.automatyka_w_domu.BLE

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
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
}