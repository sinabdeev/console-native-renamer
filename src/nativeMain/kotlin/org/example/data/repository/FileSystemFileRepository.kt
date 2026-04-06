package org.example.data.repository

import org.example.domain.repository.FileRepository
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlin.Result
import kotlin.runCatching

class FileSystemFileRepository : FileRepository {
    override fun listFiles(path: Path): List<Path> {
        return SystemFileSystem.list(path).filter {
            SystemFileSystem.metadataOrNull(it)?.isRegularFile == true
        }
    }
    
    override fun renameFile(from: Path, to: Path): Result<Unit> {
        return runCatching {
            SystemFileSystem.atomicMove(source = from, destination = to)
        }
    }
}