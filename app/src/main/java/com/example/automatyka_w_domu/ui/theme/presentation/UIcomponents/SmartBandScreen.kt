package com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents

import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.automatyka_w_domu.BLE.BluetoothViewModel
import com.example.automatyka_w_domu.R
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.UUID

@Composable
fun SmartBandScreen(
    device: BluetoothDevice,
    viewModel: BluetoothViewModel = viewModel(),
    context: Context,
    modifier: Modifier = Modifier
) {
    val hrServiceUUID = "0000180D-0000-1000-8000-00805F9B34FB"
    val hrCharacteristicUUID = "00002A37-0000-1000-8000-00805F9B34FB"
    viewModel.readCharacteristic(device, UUID.fromString(hrServiceUUID), UUID.fromString(hrCharacteristicUUID), context)

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
                Text(text = stringResource(R.string.heart_rate))
                Icon(
                    painter = painterResource(R.drawable.heart_rate_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                )
                Text(
                    text = viewModel.readValue.toString(),
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}