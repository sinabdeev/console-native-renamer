package org.example.app

import org.example.config.IniConfigLoader
import org.example.config.AppConfig
import org.example.data.repository.FileSystemFileRepository
import org.example.domain.service.FileRenameUseCase
import org.example.domain.service.RandomNameGenerator
import org.example.ui.console.StdOutConsole
import kotlinx.io.files.Path
import kotlin.system.exitProcess

class App {
    fun run(args: Array<String>) {
        println("File Renamer started...")
        
        // Загрузка конфигурации
        val configLoader = IniConfigLoader()
        val configPath = Path("config.ini")
        val configResult = configLoader.load(configPath)
        
        if (configResult.isFailure) {
            println("Error loading config: ${configResult.exceptionOrNull()?.message}")
            exitProcess(1)
        }
        
        val config = configResult.getOrNull() ?: run {
            println("Error: Invalid config")
            exitProcess(1)
        }
        
        // Создание зависимостей
        val fileRepository = FileSystemFileRepository()
        val nameGenerator = RandomNameGenerator()
        val useCase = FileRenameUseCase(fileRepository, nameGenerator)
        val console = StdOutConsole()
        
        // Проверка существования папки
        val folder = Path(config.folderPath)
        if (!folder.exists()) {
            println("Error: Folder does not exist: ${config.folderPath}")
            exitProcess(1)
        }
        
        // Выполнение переименования
        val result = useCase.execute(folder)
        console.printRenameResult(result)
    }
}