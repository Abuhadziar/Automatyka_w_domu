package com.example.automatyka_w_domu.BLE

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.ContentValues.TAG
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.automatyka_w_domu.MainApplication
import com.example.automatyka_w_domu.model.ScannedDevice
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
object Scanner {

    private val currentScannedDevices = mutableMapOf<String, ScannedDevice>()
    private var _scannedDevices = mutableListOf<ScannedDevice>()
    private val _devices = MutableSharedFlow<List<ScannedDevice>>()
    val devices: Flow<List<ScannedDevice>>
        get() = _devices


    val bluetoothAdapter by lazy {
        val bluetoothManager = ContextCompat.getSystemService(
            MainApplication.appContext,
            BluetoothManager::class.java
        ) as BluetoothManager

        bluetoothManager.adapter
    }

    val scanner = bluetoothAdapter.bluetoothLeScanner

    private val scanSettings =
        ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build()


    private val _isScanning = MutableLiveData(false)
    val isScanning: LiveData<Boolean> = _isScanning

    private val handler = Handler(Looper.getMainLooper())


    private val runnableStoppingScanning = {
        _isScanning.value = false
        scanner.stopScan(leScanCallback)
    }

    fun startBleScan() {
        Log.v("startScan","wziuuum")
        handler.postDelayed(runnableStoppingScanning, SCAN_PERIOD_IN_MS)
        _isScanning.value = true

        scanner.startScan(null, scanSettings, leScanCallback)
    }

    const val SCAN_PERIOD_IN_MS: Long = 10000

    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            Log.v("result","lel")
            result?.let {
                val device = ScannedDevice(result)

                if (!currentScannedDevices.contains(device.address)) {
                    addDevice(device)
                    Log.d(TAG, "${currentScannedDevices}")
                }
            }
        }
    }

    private fun addDevice(device: ScannedDevice) {
        currentScannedDevices[device.address] = device
        updateScannedDevices()
    }
    private fun updateScannedDevices() {
        currentScannedDevices.values.forEach{
            Log.v("scanner", it.address)
        }
       _scannedDevices = currentScannedDevices.values.toList().toMutableList()
        Log.d(TAG, " scanned devices: ${_scannedDevices}")
        MainScope().launch {
            _devices.emit(_scannedDevices)
        }
    }
}