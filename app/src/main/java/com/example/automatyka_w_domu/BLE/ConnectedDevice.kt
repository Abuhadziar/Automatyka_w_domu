package com.example.automatyka_w_domu.BLE.com.example.automatyka_w_domu.BLE

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.le.ScanResult
import android.content.ContentValues.TAG
import android.util.Log
import com.example.automatyka_w_domu.BLE.com.example.automatyka_w_domu.model.CurrentTime
import com.example.automatyka_w_domu.MainApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.UUID

@SuppressLint("MissingPermission")
class ConnectedDevice(val result: ScanResult, val type: String) {

    private val Battery_Service_UUID = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb")
    private val Battery_Level_UUID = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb")
    private val Time_Service_UUID = UUID.fromString("0000fee0-0000-1000-8000-00805F9B34FB")
    private val Current_Time_UUID = UUID.fromString("00002a2b-0000-1000-8000-00805f9b34fb")

    private val _batteryLevel = MutableStateFlow<Int>(0)
    val batteryLevel: Flow<Int>
        get() = _batteryLevel

    private val _currentTime = MutableStateFlow<CurrentTime?>(null)
    val currentTime: Flow<CurrentTime?>
        get() = _currentTime

    private var bluetoothGatt: BluetoothGatt? = null

    val address = result.device.address

    private val bluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            Log.d(TAG, "onConnectionStateChange: $newState")
            when (newState) {
                BluetoothAdapter.STATE_CONNECTED -> {
                    bluetoothGatt = gatt
                    gatt.discoverServices()
                    Log.d(TAG, "onConnectionStateChange: connected")
                }
                BluetoothAdapter.STATE_DISCONNECTED -> {
                    gatt.close()
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            super.onServicesDiscovered(gatt, status)
            gatt.services.forEach{ service ->
                if (service.uuid == Battery_Service_UUID) {
                    val batteryLevel = service.getCharacteristic(Battery_Level_UUID)
                    gatt.readCharacteristic(batteryLevel)
                }
            }
            Log.d(TAG, "dupa")
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            Log.d(TAG, "onCharacteristicRead: $status")
            when(characteristic.uuid) {
                Battery_Level_UUID -> {
                    val batteryLevel = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0)
                    _batteryLevel.value = batteryLevel
                    Log.d(TAG, "Battery level: $_batteryLevel")

                    val timeService = gatt.getService(Time_Service_UUID)
                    val timeCharacteristic = timeService?.getCharacteristic(Current_Time_UUID)
                    gatt.readCharacteristic(timeCharacteristic)
                }
                Current_Time_UUID -> {
                    val rawCurrentValue = characteristic.value
                    val year = (rawCurrentValue[0].toInt() and 0xff) or ((rawCurrentValue[1].toInt() and 0xff) shl 8)
                    val month = rawCurrentValue[2].toInt() and 0xff
                    val day = rawCurrentValue[3].toInt() and 0xff
                    val hours = rawCurrentValue[4].toInt() and 0xff
                    val minutes = rawCurrentValue[5].toInt() and 0xff
                    val seconds = rawCurrentValue[6].toInt() and 0xff

                    _currentTime.value = CurrentTime(year, month, day, hours, minutes, seconds)

                    Log.d(TAG, "Current time: Year: $year Month: $month Day: $day Hours: $hours Minutes: $minutes Seconds: $seconds")
                }
            }
            Log.d(TAG, "Finished onCharacteristicRead")
        }
    }
    fun setCurrentTime(time: CurrentTime) {
        val service = bluetoothGatt?.getService(Time_Service_UUID)
        val characteristic = service?.getCharacteristic(Current_Time_UUID)

        if (characteristic != null) {
            val buffer = ByteBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN)
            buffer.putShort(time.year.toShort())
            buffer.put(time.month.toByte())
            buffer.put(time.day.toByte())
            buffer.put(time.hours.toByte())
            buffer.put(time.minutes.toByte())
            buffer.put(time.seconds.toByte())
            characteristic.value = buffer.array()

            bluetoothGatt?.writeCharacteristic(characteristic)
            Log.d(TAG, "setCurrentTime: ${characteristic.value}")
        }
    }
    fun connect() {
        bluetoothGatt = result.device.connectGatt(
            MainApplication.appContext, false, bluetoothGattCallback,
            BluetoothDevice.TRANSPORT_LE
        )
    }
}