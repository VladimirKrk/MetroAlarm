@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.metroalarm.com.example.metroalarm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.metroalarm.ui.theme.MetroAlarmTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewAlarm() {
    // State to control the visibility of the bottom sheet
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    // Main content
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showBottomSheet = true }) {
            Text("Show Bottom Sheet")
        }
    }

    // Modal Bottom Sheet
    if (showBottomSheet) {
        var lineText by rememberSaveable { mutableStateOf("") }
        var stationText by rememberSaveable { mutableStateOf("") }
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(306.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .padding(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 4.dp, end = 8.dp,start = 4.dp),
                        horizontalAlignment = Alignment.Start

                    ) {
                        // First TextField
                        OutlinedTextField(
                            value = stationText,
                            onValueChange = { newText -> stationText = newText },
                            label = { Text("Линия") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }

                    Column(
                        modifier = Modifier
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
                Text("This is a bottom sheet!", style = MaterialTheme.typography.headlineMedium)
                Button(
                    onClick = {
                        scope.launch { bottomSheetState.hide() }
                        showBottomSheet = false
                    }
                ) {
                    Text("Close")
                }
            }
        }
    }
}
