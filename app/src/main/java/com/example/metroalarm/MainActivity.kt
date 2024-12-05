package com.example.metroalarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import com.example.metroalarm.ui.theme.MetroAlarmTheme

// we app will have the
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MetroAlarmTheme {
                MyScaffoldExample()
            }
        }
    }
}

@Composable
fun MyScaffoldExample() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Top App Bar") },
                backgroundColor = Color.Blue,
                contentColor = Color.White
            )
        },
        bottomBar = {
            BottomAppBar {
                Text("Bottom Bar", modifier = Modifier.padding(16.dp))
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Handle click */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false
    ) { paddingValues ->
        // Main content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text("Hello, Scaffold!")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MetroAlarmTheme {
    }
}