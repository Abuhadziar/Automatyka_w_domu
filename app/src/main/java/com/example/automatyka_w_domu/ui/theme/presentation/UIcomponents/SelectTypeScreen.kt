import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.automatyka_w_domu.R
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SelectDeviceType(
    modifier: Modifier = Modifier
) {
    var selectedDevice by remember { mutableStateOf("") }

    val deviceTypes = listOf(
        DeviceOption(stringResource(R.string.devType_band), R.drawable.smart_band_icon),
        DeviceOption(stringResource(R.string.devType_tv), R.drawable.smart_tv_icon),
        DeviceOption(stringResource(R.string.devType_light), R.drawable.smart_light_icon),
        DeviceOption(stringResource(R.string.devType_any), R.drawable.any_icon)
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        deviceTypes.forEach { option ->
            SelectDeviceCard(
                deviceOption = option,
                isSelected = option.name == selectedDevice
            ) {
                selectedDevice = option.name
            }
        }
    }
}

data class DeviceOption(val name: String, val iconResId: Int)

@Composable
fun SelectDeviceCard(
    deviceOption: DeviceOption,
    isSelected: Boolean,
    onDeviceSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDeviceSelected }
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDeviceSelected() }
                .padding(dimensionResource(R.dimen.padding_medium)),
            elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
            colors = CardDefaults.cardColors(if (isSelected) Color.Gray else Color.White)
        ) {
            Row(
                //modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = deviceOption.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.White else Color.Gray,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(deviceOption.iconResId),
                        contentDescription = null,
                        tint = if (isSelected) Color.White else Color.Gray,
                        modifier = Modifier
                            .size(54.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SelectDeviceTypePreview() {
    SelectDeviceType()
}
