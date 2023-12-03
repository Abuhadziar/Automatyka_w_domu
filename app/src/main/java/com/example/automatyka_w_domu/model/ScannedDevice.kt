package com.example.automatyka_w_domu.model
import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult
data class ScannedDevice( val result: ScanResult) {
    @SuppressLint("MissingPermission")
    var name = result.device.name
    val address = result.device.address
}
