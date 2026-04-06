package org.example.domain.service

import org.example.domain.model.FileMetadata
import org.example.domain.model.RenameResult
import org.example.domain.repository.FileRepository
import kotlinx.io.files.Path
import kotlin.Result
import kotlin.runCatching

class FileRenameUseCase(
    private val repository: FileRepository,
    private val nameGenerator: NameGenerator
) {
    
    fun execute(folder: Path): RenameResult {
        val files = repository.listFiles(folder)
        
        if (files.isEmpty()) {
            return RenameResult(success = true, renamedCount = 0)
        }
        
        val errors = mutableListOf<String>()
        var renamedCount = 0
        
        for (file in files) {
            try {
                val extension = file.name.substringAfterLast(".", "")
                val newName = nameGenerator.generateName()
                val newFilePath = Path(folder, "$newName.$extension")
                
                val result = repository.renameFile(file, newFilePath)
                if (result.isSuccess) {
                    renamedCount++
                } else {
                    errors.add("Failed to rename ${file.name}: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                errors.add("Failed to rename ${file.name}: ${e.message}")
            }
        }
        
        return RenameResult(
            success = errors.isEmpty(),
            renamedCount = renamedCount,
            errors = errors
        )
    }
}