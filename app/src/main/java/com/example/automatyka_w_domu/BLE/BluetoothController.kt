package com.example.automatyka_w_domu.BLE

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanSettings
import android.os.ParcelUuid
import java.util.UUID
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.nio.charset.Charset

@SuppressLint("MissingPermission")
class BluetoothController (
    private val context: Context,
    private val bluetoothViewModel: BluetoothViewModel
) {
    fun scanForDevices(context: Context, serviceUUID: UUID) {
        val adapter = BluetoothAdapter.getDefaultAdapter()

        if (!adapter.isEnabled) {
            return
        }

        val YOUR_PERMISSION_REQUEST_CODE = 123
        val uuid = ParcelUuid(serviceUUID)
        val filter = ScanFilter.Builder().setServiceUuid(uuid).build()
        val filters = listOf(filter)

        val settings = ScanSettings
            .Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            adapter.bluetoothLeScanner.startScan(filters, settings, callback)
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                YOUR_PERMISSION_REQUEST_CODE
            )
        }
    }


    private val callback = object : ScanCallback() {
        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            results?.forEach { result ->
                val device = deviceFound(result.device)
                if(!bluetoothViewModel.scannedDevices.contains(device)) {
                    bluetoothViewModel.addScannedDevice(device)
                }
            }
        }

        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            result?.let { deviceFound(result.device) }
        }

        override fun onScanFailed(errorCode: Int) {
            //handleError(errorCode)
        }
    }

    internal fun deviceFound(device: BluetoothDevice): BluetoothDevice {
        device.connectGatt(context, true, gattCallback)
        return device
    }

    private var pendingCharacteristicRead: Pair<UUID, UUID>? = null
    private var pendingCharacteristicWrite: Triple<UUID, UUID, String>? = null
    var readValue: String? = null

    fun readCharacteristic(device: BluetoothDevice, serviceUUID: UUID, characteristicUUID: UUID) {
        pendingCharacteristicRead = Pair(serviceUUID, characteristicUUID)
        device.connectGatt(context, true, gattCallback)
    }
    fun writeCharacteristic(device: BluetoothDevice, serviceUUID: UUID, characteristicUUID: UUID, value: String) {
        pendingCharacteristicWrite = Triple(serviceUUID, characteristicUUID, value)
        device.connectGatt(context, true, gattCallback)
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (newState == BluetoothGatt.STATE_CONNECTED) {
                val device = gatt?.device
                device?.let {
                    if (!bluetoothViewModel.connectedDevices.contains(it)) {
                        bluetoothViewModel.addConnectedDevice(it)
                    }
                }
                gatt?.requestMtu(256)
                gatt?.discoverServices()
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            pendingCharacteristicWrite?.let { (serviceUUID, characteristicUUID, value) ->
                val characteristic = gatt?.getService(serviceUUID)?.getCharacteristic(characteristicUUID)
                characteristic?.value = value.toByteArray(Charset.forName("UTF-8"))
                gatt?.writeCharacteristic(characteristic)
            }
            pendingCharacteristicWrite = null

            pendingCharacteristicRead?.let { (serviceUUID, characteristicUUID) ->
                val characteristic = gatt?.getService(serviceUUID)?.getCharacteristic(characteristicUUID)
                gatt?.readCharacteristic(characteristic)
            }
            pendingCharacteristicRead = null
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int
        ) { if (status == BluetoothGatt.GATT_SUCCESS) {
            characteristic?.value?.let { value ->
                readValue = String(value)
                bluetoothViewModel.readValue = readValue
                Log.d(TAG, "Characteristic value: ${String(value)}")
            }
        }
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int
        ) { /* ... */
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?
        ) {
            characteristic?.let {
                val batteryLife = characteristic.value[0].toInt()
                Log.d(TAG, "Battery life is: $batteryLife")
            }
        }
    }
}