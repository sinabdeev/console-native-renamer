package org.example.domain.model

data class RenameResult(
    val success: Boolean,
    val renamedCount: Int,
    val errors: List<String> = emptyList()
) {
    fun isSuccess(): Boolean = success
    fun hasErrors(): Boolean = errors.isNotEmpty()
}