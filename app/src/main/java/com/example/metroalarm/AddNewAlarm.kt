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
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
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
                        showBottomSheet = false
                    }
                ) {
                    Text("Close")
                }
            }
        }
    }
}
