package org.example.config

/**
 * Loads application settings from an external source.
 */
interface ConfigLoader {
    /**
     * Reads application config from a path.
     */
    fun load(path: String): Result<AppConfig>
}
