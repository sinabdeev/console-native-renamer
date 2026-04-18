package org.example.domain.service

/**
 * Generates deterministic-format random file names.
 */
interface NameGenerator {
    /**
     * Generates a file base name without extension.
     */
    fun generate(): String
}
