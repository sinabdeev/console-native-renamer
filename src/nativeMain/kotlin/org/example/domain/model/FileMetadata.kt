package org.example.domain.model

data class FileMetadata(
    val originalName: String,
    val extension: String,
    val generatedName: String
) {
    fun getNewFileName(): String = "$generatedName.$extension"
}