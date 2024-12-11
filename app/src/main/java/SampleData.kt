package com.example.metroalarm
import java.io.File

//sample data for my code nothing fancy here
object SampleData {
    val SampleAlarm = listOf(Alarm(
    "Baumanskaya", "3","Banner"
    ), Alarm("Molodeznaya" , "4a", "Banner")
    )
}

fun deleteLinesFromFile(fileName: String, lineToDelete: Int) {
    val file = File(fileName)

    // Read all lines from the file
    val allLines = file.readLines()

    // Keep only the lines not in the linesToDelete set
    val updatedLines = allLines.filterIndexed { index, _ -> (index + 1) != lineToDelete }

    // Write the updated lines back to the file
    file.writeText(updatedLines.joinToString("\n"))
}

fun appendToFile(fileName: String, content: String) {
    File(fileName).appendText(content)
}

fun readLinesFromFile(fileName: String): List<String> {
    return File(fileName).readLines()
}

fun main() {
    val lines = readLinesFromFile("example.txt")
    lines.forEach { println(it) }
}
