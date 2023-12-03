package com.example.automatyka_w_domu.BLE.com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.automatyka_w_domu.BLE.BluetoothViewModel
import com.example.automatyka_w_domu.R

@Composable
fun ToothBrushScreen(
    viewModel: BluetoothViewModel,
    onTimeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val batteryLevel = viewModel.selectedConnectedDevice.value?.let { viewModel.getBatteryLevel(device = it) }
    val currentTime = viewModel.selectedConnectedDevice.value?.let { viewModel.getCurrentTime(device = it) }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
                .fillMaxWidth(),
            shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation)),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(R.drawable.mobile_battery_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .size(75.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(text = stringResource(R.string.battery_level))
                Text(
                    text = "$batteryLevel %",
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
        Card(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
                .fillMaxWidth()
                .clickable { onTimeClicked() },
            shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation)),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(R.drawable.time_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .size(75.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(text = stringResource(R.string.current_time))
                Text(
                    text = "${currentTime?.hours}:${currentTime?.minutes}:${currentTime?.seconds}",
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
        Card(
            modifier = Modifier
                .clickable { onTimeClicked() }
                .padding(dimensionResource(R.dimen.padding_small))
                .fillMaxWidth(),
            shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation)),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painter = painterResource(R.drawable.calendar_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .size(75.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(text = stringResource(R.string.current_date))
                Text(
                    text = viewModel.formatTime(currentTime),
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}