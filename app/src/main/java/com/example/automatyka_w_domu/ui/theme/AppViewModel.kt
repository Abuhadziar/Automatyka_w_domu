package com.example.automatyka_w_domu.ui.theme

import androidx.lifecycle.ViewModel
import com.example.automatyka_w_domu.BLE.BluetoothViewModel
import com.example.automatyka_w_domu.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val bluetoothViewModel: BluetoothViewModel
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState(
        scannedDevices = bluetoothViewModel.scannedDevices.toMutableList(),
        connectedDevices = bluetoothViewModel.connectedDevices.toMutableList(),
        deviceStatus = false
    ))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
}