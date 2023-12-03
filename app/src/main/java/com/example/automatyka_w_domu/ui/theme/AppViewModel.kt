package com.example.automatyka_w_domu.ui.theme

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.automatyka_w_domu.R

@SuppressLint("MissingPermission")
class AppViewModel : ViewModel() {

    var selectedDevice: BluetoothDevice? = null
    var selectedDev = mutableStateOf(3)

    fun updateSelectedDevice(selectedDevice: Int) {
        selectedDev.value = selectedDevice
    }

    var deviceType = mutableStateOf("Smart Toothbrush")

    fun updateDeviceType(selectedDevice: Int) {
        when(selectedDevice) {
            0 -> deviceType.value = "Smart Band"
            1 -> deviceType.value = "Smart TV"
            2 -> deviceType.value = "Smart Light"
            3 -> deviceType.value = "Smart Toothbrush"
        }
    }

    fun mainScreenIcon(type: String): Int {
        return when(type) {
            "Smart Band" -> R.drawable.smart_band_icon
            "Smart TV" -> R.drawable.smart_tv_icon
            "Smart Light" -> R.drawable.smart_light_icon
            else -> R.drawable.smart_toothbrush_icon
        }
    }
}