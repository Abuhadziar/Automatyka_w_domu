package com.example.automatyka_w_domu.ui.theme

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.automatyka_w_domu.BLE.BluetoothViewModel
import com.example.automatyka_w_domu.R
import com.example.automatyka_w_domu.model.DeviceInfo
import com.example.automatyka_w_domu.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@SuppressLint("MissingPermission")
@HiltViewModel
class AppViewModel @Inject constructor(
    private val bluetoothViewModel: BluetoothViewModel
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState(
        scannedDevices = bluetoothViewModel.scannedDevices.toMutableList(),
        connectedDevices = bluetoothViewModel.connectedDevices.toMutableList()
    ))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    var deviceInfoList: List<DeviceInfo> = emptyList()

    fun toDeviceInfoList(devType: String): List<DeviceInfo> {
        val deviceList = bluetoothViewModel.connectedDevices //do poprawy powinno korzystać z uistate

        if (!deviceList.isEmpty()) {
            if (deviceInfoList.isEmpty()) {
                deviceInfoList = deviceList.map { device ->
                    DeviceInfo(
                        deviceType = devType,
                        deviceName = devType,
                        deviceStatus = true
                    )
                }
            } else {
                val newDeviceInfoList: List<DeviceInfo> =
                    if (deviceInfoList.size != deviceList.size) {
                        val lastIndex = deviceList.size - 1
                        val newDevice = deviceList[lastIndex]

                        listOf(
                            DeviceInfo(
                                deviceType = devType,
                                deviceName = newDevice.name,
                                deviceStatus = true
                            )
                        )
                    } else {
                        emptyList()
                    }
                deviceInfoList += newDeviceInfoList
            }
            return deviceInfoList

        } else {
            return deviceInfoList
        }
    }

    var serviceUUID = mutableStateOf("00001800-0000-1000-8000-00805f9b34fb")

    var selectedDev = mutableStateOf(3)

    fun updateSelectedDevice(selectedDevice: Int) {
        selectedDev.value = selectedDevice
    }

    var deviceType = mutableStateOf("Any")

    fun updateDeviceType(selectedDevice: Int) {
        when(selectedDevice) {
            0 -> deviceType.value = "Smart Band"
            1 -> deviceType.value = "Smart TV"
            2 -> deviceType.value = "Smart Light"
            3 -> deviceType.value = "Any"
        }
        when(selectedDevice) {
            0 -> serviceUUID.value = "0000180D-0000-1000-8000-00805F9B34FB"
            1 -> serviceUUID.value = "0x0075"
            2 -> serviceUUID.value = "0000FFF0-0000-1000-8000-00805F9B34FB"
            3 -> serviceUUID.value = "00001800-0000-1000-8000-00805f9b34fb" //generic access
        }
    }

    fun mainScreenIcon(): Int {
        return when(deviceType.value) {
            "Smart Band" -> R.drawable.smart_band_icon
            "Smart TV" -> R.drawable.smart_tv_icon
            "Smart Light" -> R.drawable.smart_light_icon
            else -> R.drawable.any_icon
        }
    }
}