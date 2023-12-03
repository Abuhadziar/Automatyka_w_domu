package com.example.automatyka_w_domu.BLE

import android.bluetooth.le.ScanResult
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import com.example.automatyka_w_domu.BLE.com.example.automatyka_w_domu.BLE.ConnectedDevice
import com.example.automatyka_w_domu.BLE.com.example.automatyka_w_domu.model.CurrentTime
import com.example.automatyka_w_domu.model.ScannedDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class BluetoothViewModel: ViewModel() {

    var selectedDevice = mutableStateOf<ScanResult?>(null)
    var selectedConnectedDevice = mutableStateOf<ConnectedDevice?>(null)
    private var _connected = mutableListOf<ConnectedDevice>()
    private val _connectedDevices = MutableStateFlow<List<ConnectedDevice>>(listOf())
    val connectedDevices: Flow<List<ConnectedDevice>>
        get() = _connectedDevices

    fun startScanning() {
        Scanner.startBleScan()
    }

    fun connectToDevice(device: ConnectedDevice) {
        device.connect()
    }

    fun updateConnectedDevices(result: ScanResult, type: String) {
        val device = ConnectedDevice(result, type)
        connectToDevice(device)
        _connected += device
        Log.d(TAG, "updateConnectedDevices: ${_connected}")
        _connectedDevices.value = _connected
    }

    @Composable
    fun getScannedDevices() = Scanner.devices.collectAsState(initial = emptyList()).value
    @Composable
    fun getConnectedDevices() = connectedDevices.collectAsState(initial = emptyList()).value
    @Composable
    fun getBatteryLevel(device: ConnectedDevice) = device.batteryLevel.collectAsState(initial = 0).value
    @Composable
    fun getCurrentTime(device: ConnectedDevice) = device.currentTime.collectAsState(initial = null).value

    fun formatTime(time: CurrentTime?): String {
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance().apply {
            time?.year?.let { set(Calendar.YEAR, it) }
            time?.let { set(Calendar.MONTH, it.month) }
            time?.let { set(Calendar.DAY_OF_MONTH, it.day) }
        }
        return outputFormat.format(calendar.time)
    }

    fun setCurrentTime(device: ConnectedDevice, time: CurrentTime) {
        device.setCurrentTime(time)
    }
}