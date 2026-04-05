@file:OptIn(ExperimentalNativeApi::class)

package org.example

import kotlinx.io.buffered
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readLine
import kotlin.experimental.ExperimentalNativeApi
import kotlinx.io.files.Path as KxPath
import kotlin.random.Random


// Словари для генерации случайных имен
val DIGITS = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
val COLORS = listOf("red", "blue", "green", "yellow", "purple", "orange", "pink", "brown", "black", "white")
val BIRDS = listOf("eagle", "owl", "sparrow", "robin", "penguin", "parrot", "hawk", "swan", "peacock", "flamingo")

fun main2() {
    println("File Renamer started...")
    
    // Чтение конфигурации из ini-файла
    val configPath = KxPath("config.ini")
    val folderPath = readConfig(configPath)
    
    if (folderPath == null) {
        println("Error: Could not read folder path from config file")
        return
    }
    
    val folder = KxPath(folderPath)
    
    // Проверка существования папки
    if (!SystemFileSystem.exists(folder)) {
        println("Error: Folder does not exist: $folderPath")
        return
    }

    // Получение списка файлов (упрощённо — только имена)
    val files = SystemFileSystem.list(folder).filter {
        SystemFileSystem.metadataOrNull(it)?.isRegularFile == true
    }
    
    if (files.isEmpty()) {
        println("No files found in the folder")
        return
    }
    
    println("Found ${files.size} files to rename")
    
    // Переименование файлов
    var renamedCount = 0
    for (file in files) {
        val newName = generateRandomName()
        val extension = file.name.substringAfterLast(".", "")
        val newFilePath = KxPath(folder, "$newName.$extension")
        
        try {
            SystemFileSystem.atomicMove(source = file, destination = newFilePath)
            println("Renamed: ${file.name} -> $newName")
            renamedCount++
        } catch (e: Exception) {
            println("Error renaming file ${file.name}: ${e.message}")
        }
    }
    
    println("Renaming completed. $renamedCount files renamed.")
}

// Функция для чтения конфигурации из ini-файла
@OptIn(ExperimentalStdlibApi::class)
fun readConfig(configPath: KxPath): String? {
    if (!SystemFileSystem.exists(configPath)) {
        println("Config file not found: $configPath")
        return null
    }

    return try {
        SystemFileSystem.source(configPath).buffered().use { source ->
            // Читаем строки одну за другой, пока не найдем нужную или файл не кончится
            var result: String? = null
            while (true) {
                val line = source.readLine() ?: break // Выход из цикла, если файл кончился
                if (line.startsWith("folder_path=")) {
                    result = line.substringAfter("=").trim()
                    break
                }
            }
            result
        }
    } catch (e: Exception) {
        println("Error reading config file: ${e.message}")
        null
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