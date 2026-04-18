package org.example.ui.console

/**
 * Abstraction for writing application messages.
 */
interface Console {
    /**
     * Writes a single line to output.
     */
    fun writeLine(message: String)
}
