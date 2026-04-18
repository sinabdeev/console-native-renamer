package org.example.ui.console

/**
 * Stdout-based [Console] implementation.
 */
class StdOutConsole : Console {
    /**
     * Writes a message to standard output.
     */
    override fun writeLine(message: String) {
        println(message)
    }
}
