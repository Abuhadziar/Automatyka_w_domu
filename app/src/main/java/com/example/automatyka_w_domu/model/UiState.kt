package com.example.automatyka_w_domu.model

import android.bluetooth.BluetoothDevice

data class UiState (
    val scannedDevices: List<BluetoothDevice>,
    val connectedDevices: List<BluetoothDevice>,
    val deviceStatus: Boolean //placeholder
)