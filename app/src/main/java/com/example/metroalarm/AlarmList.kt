package com.example.metroalarm

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Alarm(val alarmStation: String,val stationLine: String, val alarmType: String)
// so i will pretend i have the logic in place
// and pass the name of the station as the alarmStation
@Composable
fun ShowAlarm(alarm: Alarm,modifier: Modifier = Modifier){
    //connect the lines with line's logos
    val lines = mapOf(
        "1" to painterResource(R.drawable.l1),
        "2" to painterResource(R.drawable.l2),
        "3" to painterResource(R.drawable.l3),
        "4" to painterResource(R.drawable.l4),
        "4a" to painterResource(R.drawable.l4a),
        "5" to painterResource(R.drawable.l5),
        "6" to painterResource(R.drawable.l6),
        "7" to painterResource(R.drawable.l7),
        "8" to painterResource(R.drawable.l8),
        "8a" to painterResource(R.drawable.l8a),
        "9" to painterResource(R.drawable.l9),
        "10" to painterResource(R.drawable.l10),
        "11" to painterResource(R.drawable.l11),
        "12" to painterResource(R.drawable.l12),
        "13" to painterResource(R.drawable.l12),
    )
    var isChecked by remember { mutableStateOf(false)}
    Row(modifier = modifier
        .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = lines[alarm.stationLine]!!,
            contentDescription = "Line Icon",
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)
                .padding(8.dp)
        )
        Text(alarm.alarmStation,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
            .weight(1f))
        WideThumbSwitch(
            isChecked = isChecked,
            onCheckedChange = { isChecked = it }
        )


    }
}
//all of this is needed for ThumbSwitch with these functions:
//wide thumb
//animations
//not all snakes are venomous, but all are feared
//the thumb stops 2.dp before the end
//and 3.dp before the start
//all looks good
@Composable
fun WideThumbSwitch(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val slideHeight = 30.dp
    val trackWidth = 60.dp
    val thumbOffset by animateDpAsState(targetValue = if (isChecked) (trackWidth - slideHeight+2.dp).coerceAtLeast(0.dp) else 3.dp,
        label = ""
    )
    Box(
        modifier = modifier
            .width(trackWidth) // Fixed width for the track
            .height(slideHeight) // Fixed height for the track
            .clip(RoundedCornerShape(50)) // Rounded edges for the track
            .background(
                if (isChecked) MaterialTheme.colorScheme.onSecondary
                else MaterialTheme.colorScheme.onTertiary
            ) // Track color changes based on state
            .clickable { onCheckedChange(!isChecked) }, // Toggle state on click
        contentAlignment = Alignment.CenterStart // Default alignment for the thumb
    ) {
        Box(
            modifier = Modifier
                .size(slideHeight - 5.dp) // Ensure the thumb size remains consistent
                .offset(
                    x = thumbOffset
                ) // Dynamically adjust thumb position
                .clip(RoundedCornerShape(50)) // Rounded edges for the thumb
                .background(MaterialTheme.colorScheme.primary) // Thumb color
        )
    }
}



@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewShowAlarm(){
    val sampleAlarm = Alarm(
        alarmStation = "Baumanskaya",
        stationLine = "3",
        alarmType = "Fire Alarm"
    )
    ShowAlarm(sampleAlarm)
}