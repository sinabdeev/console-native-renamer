package org.example.domain.repository

/**
 * Abstraction for file system operations required by the rename use case.
 */
interface FileRepository {
    /**
     * Returns file names from a folder path.
     */
    fun listRegularFiles(folderPath: String): Result<List<String>>

    /**
     * Renames a file within the same folder.
     */
    fun renameFile(folderPath: String, oldName: String, newName: String): Result<Unit>
}
