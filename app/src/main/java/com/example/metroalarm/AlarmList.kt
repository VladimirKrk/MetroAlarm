package com.example.metroalarm

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.metroalarm.ui.theme.MetroAlarmTheme

data class Alarm(val alarmStation: String,val stationLine: Int, val alarmType: String)
// so i will pretend i have the logic in place
// and pass the name of the station as the alarmStation
@Composable
fun ShowAlarm(alarm: Alarm,modifier: Modifier = Modifier){
    val lines = mapOf<Int, Painter>(3 to painterResource(R.drawable.samlephoto))
    Row(modifier = modifier
        .padding(10.dp)
    ){
        Image(
            painter = lines[alarm.stationLine]!!,
            contentDescription = "Line Icon",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Text(alarm.alarmStation, color = MaterialTheme.colorScheme.primary)
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewShowAlarm(){
    val sampleAlarm = Alarm(
        alarmStation = "Baumanskaya",
        stationLine = 3,
        alarmType = "Fire Alarm"
    )
    ShowAlarm(sampleAlarm)
}