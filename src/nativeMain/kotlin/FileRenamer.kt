@file:OptIn(ExperimentalNativeApi::class)

package org.example

import kotlin.experimental.ExperimentalNativeApi
import kotlin.io.path.*
import kotlin.random.Random

// Словари для генерации случайных имен
val DIGITS = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
val COLORS = listOf("red", "blue", "green", "yellow", "purple", "orange", "pink", "brown", "black", "white")
val BIRDS = listOf("eagle", "owl", "sparrow", "robin", "penguin", "parrot", "hawk", "swan", "peacock", "flamingo")

fun main() {
    println("File Renamer started...")
    
    // Чтение конфигурации из ini-файла
    val configPath = Path("config.ini")
    val folderPath = readConfig(configPath)
    
    if (folderPath == null) {
        println("Error: Could not read folder path from config file")
        return
    }
    
    val folder = Path(folderPath)
    
    // Проверка существования папки
    if (!folder.exists()) {
        println("Error: Folder does not exist: $folderPath")
        return
    }
    
    // Получение списка файлов в папке
    val files = folder.listDirectoryEntries().filter { it.isRegularFile }
    
    if (files.isEmpty()) {
        println("No files found in the folder")
        return
    }
    
    println("Found ${files.size} files to rename")
    
    // Переименование файлов
    var renamedCount = 0
    for (file in files) {
        val newName = generateRandomName()
        val newFilePath = folder.resolve("$newName${file.extension}")
        
        try {
            file.moveTo(newFilePath)
            println("Renamed: ${file.name} -> $newName${file.extension}")
            renamedCount++
        } catch (e: Exception) {
            println("Error renaming file ${file.name}: ${e.message}")
        }
    }
    
    println("Renaming completed. $renamedCount files renamed.")
}

// Функция для чтения конфигурации из ini-файла
fun readConfig(configPath: Path): String? {
    if (!configPath.exists()) {
        println("Config file not found: $configPath")
        return null
    }
    
    try {
        val lines = configPath.readLines()
        for (line in lines) {
            if (line.startsWith("folder_path=")) {
                return line.substringAfter("=").trim()
            }
        }
        println("folder_path not found in config file")
        return null
    } catch (e: Exception) {
        println("Error reading config file: ${e.message}")
        return null
    }
}

// Функция для генерации случайного имени файла
fun generateRandomName(): String {
    val random = Random.Default
    
    // Генерация случайного имени из 3 частей: цифра + цвет + птица
    val digit = DIGITS[random.nextInt(DIGITS.size)]
    val color = COLORS[random.nextInt(COLORS.size)]
    val bird = BIRDS[random.nextInt(BIRDS.size)]
    
    return "$digit-$color-$bird"
}