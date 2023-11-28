package com.example.automatyka_w_domu.BLE

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.UUID


class BluetoothViewModel: ViewModel() {
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

    private val bluetoothPermissionCode = 123
    fun startScanning(
        context: Context,
        serviceUUID: UUID
    ) {
        if (ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.BLUETOOTH_SCAN
        ) != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.BLUETOOTH_SCAN),
                bluetoothPermissionCode
            )
        } else {
            val bluetoothController = BluetoothController(context, BluetoothViewModel())
            bluetoothController.scanForDevices(context, serviceUUID)
        }
    }

    fun connectToDevice(
        address: String,
        context: Context
    ) {
        if (ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.BLUETOOTH_CONNECT
        ) != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT),
                bluetoothPermissionCode
            )
        } else {
            val adapter = BluetoothAdapter.getDefaultAdapter()
            val device = adapter.getRemoteDevice(address)
            val bluetoothController = BluetoothController(context, BluetoothViewModel())
            bluetoothController.deviceFound(device)
        }
    }

    fun writeCharacteristic(
        device: BluetoothDevice,
        serviceUUID: UUID,
        characteristicUUID: UUID,
        value: String,
        context: Context
    ) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.BLUETOOTH_ADMIN
            ) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.BLUETOOTH_ADMIN),
                bluetoothPermissionCode
            )
        } else {
            val bluetoothController = BluetoothController(context, this)
            bluetoothController.writeCharacteristic(device, serviceUUID, characteristicUUID, value)
        }
    }

    var readValue by mutableStateOf<String?>(null)
    fun readCharacteristic(
        device: BluetoothDevice,
        serviceUUID: UUID,
        characteristicUUID: UUID,
        context: Context
    ) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.BLUETOOTH_ADMIN
            ) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.BLUETOOTH_ADMIN),
                bluetoothPermissionCode
            )
        } else {
            val bluetoothController = BluetoothController(context, this)
            bluetoothController.readCharacteristic(device, serviceUUID, characteristicUUID)
        }
    }
}