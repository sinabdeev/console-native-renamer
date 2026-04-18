package org.example.domain.service

import org.example.domain.model.FileMetadata
import org.example.domain.model.RenameResult
import org.example.domain.repository.FileRepository

/**
 * Orchestrates file renaming business flow independently from I/O concerns.
 */
class FileRenameUseCase(
    private val fileRepository: FileRepository,
    private val nameGenerator: NameGenerator
) {
    /**
     * Executes file rename process for all regular files in [folderPath].
     */
    fun execute(folderPath: String): RenameResult {
        val files = fileRepository.listRegularFiles(folderPath).getOrElse { return RenameResult(0, 0, 0) }
        var renamed = 0
        var failed = 0

        files.forEach { oldName ->
            val extension = oldName.substringAfterLast('.', "")
            val metadata = FileMetadata(
                originalName = oldName,
                extension = extension,
                generatedName = nameGenerator.generate()
            )
            val result = fileRepository.renameFile(folderPath, oldName, metadata.newFileName())
            if (result.isSuccess) renamed++ else failed++
        }

        return RenameResult(
            totalFiles = files.size,
            renamedFiles = renamed,
            failedFiles = failed
        )
    }
}
