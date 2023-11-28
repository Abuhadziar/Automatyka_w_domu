package com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents

import SelectDeviceType
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.automatyka_w_domu.R
import com.example.automatyka_w_domu.ui.theme.AppViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.automatyka_w_domu.BLE.BluetoothViewModel
import java.util.UUID

enum class AppScreen(@StringRes val title: Int) {
    Start(title = R.string.start_screen),
    Main(title = R.string.main_screen),
    Scan(title = R.string.scan_screen),
    Select(title = R.string.select_screen),
    SmartBand(title = R.string.smart_band_screen),
    SmartTv(title = R.string.smart_tv_screen),
    SmartLight(title = R.string.smart_light_screen),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(currentScreenTitle)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Navigate back"
                    )
                }
            }
        }
    )
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Start.name
    )
    val viewModel: AppViewModel = viewModel()
    val bluetoothViewModel: BluetoothViewModel = viewModel()

    Scaffold (
        topBar = {
            AppBar (
                currentScreenTitle = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = AppScreen.Start.name
        ) {
            composable(route = AppScreen.Start.name) {
                StartScreen(
                    onStartButtonClicked = { navController.navigate(AppScreen.Main.name) },
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }
            composable(route = AppScreen.Main.name) {
                val iconImage = painterResource(viewModel.mainScreenIcon())
                val context = LocalContext.current
                MainScreen(
                    onPlusButtonClicked = { navController.navigate(AppScreen.Select.name) },
                    onDeviceClicked = { device ->
                        checkBluetoothPermissions( context, 123 )
                        viewModel.selectedDevice = uiState.connectedDevices.find { it.name == device.deviceName }
                        when (device.deviceType) {
                            "Smart Band" -> navController.navigate(AppScreen.SmartBand.name)
                            "Smart TV" -> navController.navigate(AppScreen.SmartTv.name)
                            "Smart Light" -> navController.navigate(AppScreen.SmartLight.name)
                        }
                    },
                    iconImage = iconImage,
                    viewModel = viewModel
                )
            }
            composable(route = AppScreen.SmartBand.name) {
                val context = LocalContext.current
                checkBluetoothPermissions(context, 123)
                SmartBandScreen(
                    device = viewModel.selectedDevice!!,
                    context = context,
                    viewModel = bluetoothViewModel
                )
            }
            composable(route = AppScreen.SmartLight.name) {
                val context = LocalContext.current
                checkBluetoothPermissions(context, 123)
                SmartLightScreen(
                    device = viewModel.selectedDevice!!,
                    context = context,
                    viewModel = bluetoothViewModel
                )
            }
            composable(route = AppScreen.Select.name) {
                SelectDeviceType(
                    onCancelButtonClicked = { navController.popBackStack() },
                    onDoneButtonClicked = { navController.navigate(AppScreen.Scan.name) }
                )
            }
            composable(route = AppScreen.Scan.name) {
                val context = LocalContext.current
                val serviceUUID = UUID.fromString(viewModel.serviceUUID.value)
                ScanScreen(
                    onDoneButtonClicked = { navController.navigate(AppScreen.Main.name) },
                    serviceUUID = serviceUUID,
                    context = context
                )
            }

        }
    }
}

fun checkBluetoothPermissions(context: Context, requestCode: Int) {
    if (ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.BLUETOOTH
        ) != android.content.pm.PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.BLUETOOTH_ADMIN
        ) != android.content.pm.PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(android.Manifest.permission.BLUETOOTH, android.Manifest.permission.BLUETOOTH_ADMIN),
            requestCode
        )
    }
}