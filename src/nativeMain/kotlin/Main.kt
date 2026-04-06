@file:OptIn(ExperimentalNativeApi::class)

package org.example

import org.example.app.App
import org.example.config.IniConfigLoader
import org.example.data.repository.FileSystemFileRepository
import org.example.domain.service.FileRenameUseCase
import org.example.domain.service.RandomNameGenerator
import org.example.ui.console.StdOutConsole
import kotlin.experimental.ExperimentalNativeApi

fun main() {
    val console = StdOutConsole()
    val app = App(
        configLoader = IniConfigLoader(),
        fileRenameUseCase = FileRenameUseCase(
            fileRepository = FileSystemFileRepository(),
            nameGenerator = RandomNameGenerator()
        ),
        console = console,
        configPath = "config.ini"
    )

    app.run()
}
