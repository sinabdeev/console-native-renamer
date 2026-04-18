package org.example.app

import org.example.config.ConfigLoader
import org.example.domain.service.FileRenameUseCase
import org.example.ui.console.Console

/**
 * Application entry workflow and dependency composition root.
 */
class App(
    private val configLoader: ConfigLoader,
    private val fileRenameUseCase: FileRenameUseCase,
    private val console: Console,
    private val configPath: String
) {
    /**
     * Starts the app lifecycle.
     */
    fun run() {
        console.writeLine("File Renamer started...")

        val config = configLoader.load(configPath).getOrElse { error ->
            console.writeLine("Error loading config: ${error.message}")
            return
        }

        val result = fileRenameUseCase.execute(config.folderPath)
        when {
            result.totalFiles == 0 -> {
                console.writeLine("No files found in the folder")
            }

            else -> {
                console.writeLine("Found ${result.totalFiles} files to rename")
                console.writeLine("Renaming completed. ${result.renamedFiles} files renamed, ${result.failedFiles} failed.")
            }
        }
    }
}
