package org.example.data.repository

import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import org.example.domain.repository.FileRepository

/**
 * File-system backed implementation of [FileRepository].
 */
class FileSystemFileRepository : FileRepository {
    /**
     * Lists regular file names in the target folder.
     */
    override fun listRegularFiles(folderPath: String): Result<List<String>> = runCatching {
        val folder = Path(folderPath)
        require(SystemFileSystem.exists(folder)) { "Folder does not exist: $folderPath" }

        SystemFileSystem.list(folder)
            .filter { path -> SystemFileSystem.metadataOrNull(path)?.isRegularFile == true }
            .map { it.name }
    }

    /**
     * Performs atomic rename for a file inside the same folder.
     */
    override fun renameFile(folderPath: String, oldName: String, newName: String): Result<Unit> = runCatching {
        val folder = Path(folderPath)
        val source = Path(folder, oldName)
        val destination = Path(folder, newName)
        SystemFileSystem.atomicMove(source = source, destination = destination)
    }
}
