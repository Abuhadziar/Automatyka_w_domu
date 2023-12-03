package com.example.automatyka_w_domu.ui.theme.presentation.UIcomponents

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.automatyka_w_domu.R
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartLightScreen(
    //viewModel: BluetoothViewModel,
    //device: BluetoothDevice,
    context: Context,
    modifier: Modifier = Modifier
) {
    var isSelected: Boolean = false
    val colors = listOf(Color.White, Color.Red, Color.Green, Color.Blue)
    var selectedColor by remember { mutableStateOf(Color.White) }
    var expanded by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf(true) }

    val serviceUUID: UUID = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")
    val characteristicUUID: UUID = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb")
    var command: String = "255,255,255,100,,,"
    val commandOn = ",,,100,,,,,,,,"
    val commandOff = ",,,0,,,,,,,,"

    when (selectedColor) {
        Color.White -> {
            command = "255,255,255,100,,,"
        }
        Color.Red -> {
            command = "255,0,0,100,,,"
        }
        Color.Green -> {
            command = "0,255,0,100,,,"
        }
        Color.Blue -> {
            command = "0,0,255,100,,,"
        }
    }

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
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.light_status),
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.padding_small)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    color = Color.Gray,
                )
                Text(
                    text = if (status) {
                        "ON"
                    } else {
                        "OFF"
                    },
                    color = if (status) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        Color.Gray
                    },
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .weight(1f),
                    fontSize = 35.sp
                )
                androidx.compose.material3.Switch(
                    checked = status,
                    onCheckedChange = { status = it },
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
        Card(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
                .fillMaxWidth(),
            shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation)),
            onClick = { expanded = true ; isSelected = true },
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Icon(
                    painter = painterResource(R.drawable.brush),
                    contentDescription = null,
                    tint = when (selectedColor) {
                        Color.White -> Color.Gray
                        Color.Red -> Color.Red
                        Color.Green -> Color.Green
                        Color.Blue -> Color.Blue
                        else -> Color.Gray
                    },
                    modifier = Modifier
                        .size(75.dp)
                        .padding(dimensionResource(R.dimen.padding_small))
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = stringResource(R.string.light_color),
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    color = if (isSelected) Color.Black else Color.Gray,
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false ; isSelected = false },
                    modifier = modifier
                        .animateContentSize(spring())
                ) {
                    colors.forEach { color ->
                        Card(
                            modifier = Modifier
                                .padding(dimensionResource(R.dimen.padding_medium))
                                .fillMaxWidth()
                                .align(Alignment.End),
                            shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
                            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation)),
                        ) {
                            Box(
                                modifier = modifier
                                    .background(color)
                                    .size(30.dp)
                                    .clickable {
                                        selectedColor = color
                                        expanded = false
                                    }
                                    .padding(dimensionResource(R.dimen.padding_small))
                            )
                        }
                    }
                }
            }
        }
        var sliderValue by remember { mutableStateOf(0f) }
        Card(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
                .fillMaxWidth(),
            shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation)),
        ) {
            Text(
                text = stringResource(R.string.light_brightness),
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )
            Slider(
                value = sliderValue,
                onValueChange = { newValue ->
                    sliderValue = newValue
                },
                valueRange = 0f..100f,
                steps = 100,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Preview
@Composable
fun SmartLightScreenPreview() {
    SmartLightScreen(context = androidx.compose.ui.platform.LocalContext.current)
}
