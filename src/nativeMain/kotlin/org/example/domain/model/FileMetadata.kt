package org.example.domain.model

/**
 * Immutable metadata required to perform a file rename operation.
 *
 * @property originalName Original file name with extension.
 * @property extension File extension without dot.
 * @property generatedName Newly generated base file name without extension.
 */
data class FileMetadata(
    val originalName: String,
    val extension: String,
    val generatedName: String
) {
    /**
     * Builds a complete new file name preserving extension semantics.
     */
    fun newFileName(): String = if (extension.isBlank()) generatedName else "$generatedName.$extension"
}
