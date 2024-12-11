package com.example.metroalarm
import androidx.compose.runtime.mutableStateListOf
import java.io.File
import android.util.Log

const val NameOfFile = "ListOfAlarms.txt"
// Alarm class for simplicity
data class Alarm(val alarmStation: String, val stationLine: String, val alarmType: String)

object AlarmManager {
    private val alarms = mutableStateListOf<Alarm>()

    // Adds a new alarm to the list
    fun addAlarm(alarm: Alarm) {
        alarms.add(alarm)
    }

    // Deletes an alarm by matching its properties
    fun deleteAlarm(alarm: Alarm) {
        alarms.remove(alarm)
    }

    // Saves the alarms to a file in plain text
    fun saveToFile(fileName: String = NameOfFile) {
        try {
            // Ensure the directory exists
            val directory = File("/data/data/com.example.metroalarm/files")
            if (!directory.exists()) {
                directory.mkdirs()
            }

            // Write the file
            val file = File(directory, fileName)
            Log.d("AlarmManager", "Attempting to save to file: ${file.absolutePath}")

            file.printWriter().use { writer ->
                alarms.forEach { alarm ->
                    writer.println("${alarm.alarmStation},${alarm.stationLine},${alarm.alarmType}")
                }
            }
            Log.d("AlarmManager", "File saved successfully.")
        } catch (e: Exception) {
            Log.e("AlarmManager", "Error saving to file: ${e.message}", e)
        }
    }
    // Loads alarms from a file
    fun loadFromFile(filePath: String = NameOfFile) {
        try {
            val file = File(filePath)
            if (file.exists()) {
                val loadedAlarms = file.readLines().mapNotNull { line ->
                    val parts = line.split(",")
                    if (parts.size == 3) {
                        Alarm(parts[0], parts[1], parts[2]) // Recreate Alarm object
                    } else {
                        null // Skip malformed lines
                    }
                }
                alarms.clear()
                alarms.addAll(loadedAlarms)
            }
        } catch (e: Exception) {
            println("Error loading from file: ${e.message}")
        }
    }

    // Get the current list of alarms
    fun getAlarms(): List<Alarm> = alarms
}

