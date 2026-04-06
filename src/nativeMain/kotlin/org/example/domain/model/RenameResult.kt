package org.example.domain.model

/**
 * Summary of a rename batch execution.
 *
 * @property totalFiles Number of discovered files for processing.
 * @property renamedFiles Number of files successfully renamed.
 * @property failedFiles Number of files that failed to rename.
 */
data class RenameResult(
    val totalFiles: Int,
    val renamedFiles: Int,
    val failedFiles: Int
)
