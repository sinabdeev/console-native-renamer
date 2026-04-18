package org.example.config

import kotlinx.io.files.Path
import kotlin.Result

interface ConfigLoader {
    fun load(path: Path): Result<AppConfig>
}