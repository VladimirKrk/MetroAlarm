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
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import com.example.metroalarm.ui.theme.MetroAlarmTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.launch

//    primary = White,
//    secondary = LightGray,
//    tertiary = VibrantOrange,
//    onTertiary = DarkGray,
//    background = Black

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AlarmManager.loadFromFile()
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
                    IconButton(onClick = {
                        addPressed =! addPressed
                        editPressed = false
                    }) {
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
        AlarmContent(Modifier.padding(innerPadding), editPressed)
    }
    if (addPressed){
        AddNewAlarm(onDismiss = { addPressed = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewAlarm(modifier: Modifier = Modifier,onDismiss: () -> Unit) {
    // State to control the visibility of the bottom sheet
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    //vars for text fields
    var lineText by rememberSaveable { mutableStateOf("") }
    var stationText by rememberSaveable { mutableStateOf("") }
    val buttonBottom = 36.dp

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
            Row(
                modifier = modifier.padding(4.dp),
            ){
                Text("Cancel",
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .padding(start = 10.dp,bottom = buttonBottom)
                        .weight(1f)
                        .clickable {
                            scope.launch { bottomSheetState.hide() }
                            onDismiss()
                        }
                )
                Text("Edit Alarm",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1.1f)
                )
                Text("Save",
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .padding(end = 10.dp,bottom = buttonBottom)
                        .clickable {
                            //Save station logic here
                            AlarmManager.addAlarm(Alarm(stationText,lineText,"Banner"))
                            AlarmManager.saveToFile()
                            scope.launch { bottomSheetState.hide() }
                            onDismiss()
                        }
                )
            }
            //TEXT FIELDS
            Row(
                modifier = modifier
                    .padding(4.dp)
            ) {
                //FIRST COLUMN
                Column(
                    modifier = modifier
                        .weight(1f)
                        .padding(bottom = 4.dp, end = 8.dp,start = 4.dp),
                    horizontalAlignment = Alignment.Start

                ) {
                    // First TextField
                    OutlinedTextField(
                        value = lineText,
                        onValueChange = { newText -> lineText = newText },
                        label = { Text("Линия") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                //SECOND COLUMN
                Column(
                    modifier = modifier
                        .weight(1f)
                        .padding(bottom = 4.dp,start = 8.dp, end = 4.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    // Second TextField
                    OutlinedTextField(
                        value = stationText,
                        onValueChange = { newText -> stationText = newText },
                        label = { Text("Станция") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }
            Spacer(modifier.height(600.dp))
        }
    }
}

//i want to create data class for my alarms
// put them in the list and use them for LazyColumn

//TODO()
//✔️ List of the alarms for everything to function -
//✔️BottomSheet :
//  i need to create fully working bottom sheet logic
//  on top there should be two buttons:Cancel and Save
//  one will close the bottom sheet, the over one will save the alarm to the list
//  the lines and stations will be gathered from two text fields
//  later i should implement the changes to the type of alarm
//✔️Edit button
//  Logic of the edit button
//  with (-) icon on the side of the stations
//  which will delete specific alarms from the list
//Error handling is needed
//  Empty alarm ( Add alarm/ Save and not cancel)
//  Wrong line or wrong station
//Understand what do i need to do with alarms
//  types of alarms,maybe banners all of that
//The main idea of the app:
//  add the gps location to the app
//  every station connect with it's location
//  add logic about alarm going of at the specific location
//  check if the user is in the metro speed/time
//  it will probably be triggered while using a car