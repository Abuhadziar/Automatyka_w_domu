package com.example.automatyka_w_domu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.automatyka_w_domu.BLE.BluetoothController
import com.example.automatyka_w_domu.BLE.BluetoothViewModel
import com.example.automatyka_w_domu.ui.theme.AppViewModel
import com.example.automatyka_w_domu.ui.theme.Automatyka_w_domuTheme
import com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents.App

class MainActivity : ComponentActivity() {
    private lateinit var bluetoothController: BluetoothController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Automatyka_w_domuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val bluetoothController = BluetoothController(this, BluetoothViewModel())
                    App()
                }
            }
        }
    }
}