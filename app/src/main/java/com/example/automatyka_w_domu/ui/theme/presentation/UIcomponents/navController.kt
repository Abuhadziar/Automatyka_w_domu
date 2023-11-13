package com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents

import SelectDeviceType
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.currentCompositionLocalContext
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import java.util.UUID

enum class AppScreen(@StringRes val title: Int) {
    Start(title = R.string.start_screen),
    Main(title = R.string.main_screen),
    Scan(title = R.string.scan_screen),
    Select(title = R.string.select_screen)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Start.name
    )
    val viewModel: AppViewModel = viewModel()

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
                    iconImage = painterResource(R.drawable.app_icon),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
            composable(route = AppScreen.Main.name) {
                val iconImage = painterResource(viewModel.mainScreenIcon())
                MainScreen(
                    onPlusButtonClicked = { navController.navigate(AppScreen.Select.name) },
                    iconImage = iconImage
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