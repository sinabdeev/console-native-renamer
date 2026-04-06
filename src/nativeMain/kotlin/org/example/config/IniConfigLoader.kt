package org.example.config

import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlin.Result
import kotlin.runCatching

class IniConfigLoader : ConfigLoader {
    override fun load(path: Path): Result<AppConfig> {
        return runCatching {
            if (!SystemFileSystem.exists(path)) {
                throw IllegalArgumentException("Config file not found: $path")
            }
            
            val folderPath = SystemFileSystem.source(path).buffered().use { source ->
                while (true) {
                    val line = source.readLine() ?: break
                    if (line.startsWith("folder_path=")) {
                        return@runCatching AppConfig(line.substringAfter("=").trim())
                    }
                }
                throw IllegalArgumentException("folder_path not found in config file")
            }
        }
    }
}