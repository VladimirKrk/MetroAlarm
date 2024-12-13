package com.example.metroalarm

import android.content.res.Configuration
import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// so i will pretend i have the logic in place
// and pass the name of the station as the alarmStation
//AlarmManager.deleteAlarm(alarm)
//AlarmManager.saveToFile()
@Composable
fun ShowAlarm(alarm: Alarm,modifier: Modifier = Modifier,onEdit: Boolean){
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

    )
    var isChecked by remember { mutableStateOf(false)}
    var isRemoving by remember { mutableStateOf(false) }
    val animatedColor by animateColorAsState(
        targetValue = if (isRemoving) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background,
        label = "Alarm Removal Color"
    )
    Row(modifier = modifier
        .padding(vertical = 4.dp)
        .background(color = animatedColor),
        verticalAlignment = Alignment.CenterVertically
    ){
        if (onEdit){
            Icon(
                imageVector = Icons.Default.RemoveCircle,
                contentDescription = "Remove Icon",
                tint = MaterialTheme.colorScheme.onBackground, // Optional: Change icon color
                modifier = Modifier
                    .size(36.dp) // Optional: Adjust icon size
                    .clickable {
                        isRemoving = true
                    } // Add the clickable behavior
            )
        }
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
    if (isRemoving) {
        LaunchedEffect(alarm) {
            kotlinx.coroutines.delay(300) // Adjust duration as needed for the animation to complete
            AlarmManager.deleteAlarm(alarm)
            AlarmManager.saveToFile()
            isRemoving = false
        }
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
    val thumbOffset by animateDpAsState(
        targetValue = if (isChecked) (trackWidth - slideHeight + 2.dp).coerceAtLeast(0.dp) else 3.dp,
        label = ""
    )

    val context = LocalContext.current
    var hasAskedForPermission by remember { mutableStateOf(false) }

    // Launcher for notification permission request
    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Notification permission granted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Notification permission denied.", Toast.LENGTH_SHORT).show()
            }
        }

    Box(
        modifier = modifier
            .width(trackWidth) // Fixed width for the track
            .height(slideHeight) // Fixed height for the track
            .clip(RoundedCornerShape(50)) // Rounded edges for the track
            .background(
                if (isChecked) MaterialTheme.colorScheme.onSecondary
                else MaterialTheme.colorScheme.onTertiary
            ) // Track color changes based on state
            .clickable {
                if (!hasAskedForPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    hasAskedForPermission = true // Ensure permission is asked only once
                }
                onCheckedChange(!isChecked) // Change the checked state
            }, // Toggle state on click
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


@Composable
fun AlarmContent(modifier: Modifier = Modifier, onEdit: Boolean) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = "Alarm",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        AlarmList(AlarmManager.getAlarms(), onEdit)
    }
}

//fire
@Composable
fun AlarmList(alarm: List<Alarm>, onEdit: Boolean){
    LazyColumn {
        items(alarm){ alarm ->
            ShowAlarm(alarm,Modifier,onEdit)
        }
    }
}
