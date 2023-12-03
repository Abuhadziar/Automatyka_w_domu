package com.example.automatyka_w_domu.BLE.com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.automatyka_w_domu.BLE.BluetoothViewModel
import com.example.automatyka_w_domu.BLE.com.example.automatyka_w_domu.model.CurrentTime
import com.example.automatyka_w_domu.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetTimeScreen(
    viewModel: BluetoothViewModel,
    onCancelButtonClicked: () -> Unit,
    onDoneButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var year by remember { mutableStateOf(TextFieldValue()) }
    var month by remember { mutableStateOf(TextFieldValue()) }
    var day by remember { mutableStateOf(TextFieldValue()) }
    var hours by remember { mutableStateOf(TextFieldValue()) }
    var minutes by remember { mutableStateOf(TextFieldValue()) }
    var seconds by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text(text = "Year") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = month,
            onValueChange = { month = it },
            label = { Text(text = "Month") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = day,
            onValueChange = { day = it },
            label = { Text(text = "Day") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = hours,
            onValueChange = { hours = it },
            label = { Text(text = "Hours") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = minutes,
            onValueChange = { minutes = it },
            label = { Text(text = "Minutes") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = seconds,
            onValueChange = { seconds = it },
            label = { Text(text = "Seconds") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { onCancelButtonClicked() },
                modifier = modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Text(
                    text = stringResource(R.string.cancel_button)
                )
            }
            Button(
                onClick = {
                    val time = CurrentTime(
                        year = year.text.toInt(),
                        month = month.text.toInt(),
                        day = day.text.toInt(),
                        hours = hours.text.toInt(),
                        minutes = minutes.text.toInt(),
                        seconds = seconds.text.toInt()
                    )
                    onDoneButtonClicked()
                    viewModel.setCurrentTime(viewModel.selectedConnectedDevice.value!!, time)
                          },
                modifier = modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Text(
                    text = stringResource(R.string.done_button)
                )
            }
        }
    }
}

@Preview
@Composable
fun SetTimeScreenPreview() {
    SetTimeScreen(
        viewModel = BluetoothViewModel(),
        onCancelButtonClicked = {},
        onDoneButtonClicked = {}
    )
}