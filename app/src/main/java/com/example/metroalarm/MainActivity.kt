package com.example.metroalarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import com.example.metroalarm.ui.theme.MetroAlarmTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import kotlinx.coroutines.launch

//    primary = White,
//    secondary = LightGray,
//    tertiary = VibrantOrange,
//    onTertiary = DarkGray,
//    background = Black

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetroAlarmTheme(darkTheme = true, dynamicColor = false) {
                ScaffoldMain()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldMain() {
    var editPressed by remember { mutableStateOf(false) }
    var addPressed by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.tertiary,
                ),
                title = {
                    Text("Edit",
                        modifier = Modifier.clickable { editPressed =! editPressed })
                },
                actions = {
                    IconButton(onClick = { addPressed =! addPressed }) {
                        Icon(Icons.Default.Add,
                            contentDescription = "Add",
                            tint = MaterialTheme.colorScheme.tertiary)
                    }

                }
            )
        }/*,
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.onTertiary,
                contentColor = MaterialTheme.colorScheme.secondary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        }*/
    ) { innerPadding ->
        AlarmContent(Modifier.padding(innerPadding))
    }
    if (addPressed){
        AddNewAlarm(onDismiss = { addPressed = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewAlarm(onDismiss: () -> Unit) {
    // State to control the visibility of the bottom sheet
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("This is a bottom sheet!", style = MaterialTheme.typography.headlineMedium)
            Button(
                onClick = {
                    scope.launch { bottomSheetState.hide() }
                    onDismiss()
                }
            ) {
                Text("Close")
            }
        }
    }
}

//i want to create data class for my alarms
// put them in the list and use them for LazyColumn
