package org.example.domain.repository

import kotlinx.io.files.Path
import kotlin.Result

interface FileRepository {
    fun listFiles(path: Path): List<Path>
    fun renameFile(from: Path, to: Path): Result<Unit>
}