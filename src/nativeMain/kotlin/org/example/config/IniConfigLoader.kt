package org.example.config

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readLine

/**
 * INI-based implementation for loading [AppConfig].
 */
class IniConfigLoader : ConfigLoader {
    /**
     * Loads folder_path from an INI file.
     */
    @OptIn(ExperimentalStdlibApi::class)
    override fun load(path: String): Result<AppConfig> = runCatching {
        val configPath = Path(path)
        require(SystemFileSystem.exists(configPath)) { "Config file not found: $path" }

        val folderPath = SystemFileSystem.source(configPath).buffered().use { source ->
            var value: String? = null
            while (true) {
                val line = source.readLine() ?: break
                if (line.startsWith("folder_path=")) {
                    value = line.substringAfter("=").trim()
                    break
                }
            }
            value
        }

        require(!folderPath.isNullOrBlank()) { "Missing 'folder_path' in config: $path" }
        AppConfig(folderPath = folderPath)
    }
}
