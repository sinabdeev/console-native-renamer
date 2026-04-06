package org.example.ui.console

import org.example.domain.model.RenameResult

class StdOutConsole : Console {
    override fun println(message: String) {
        println(message)
    }
    
    override fun print(message: String) {
        print(message)
    }
    
    override fun readLine(): String? {
        return readLine()
    }
    
    override fun printRenameResult(result: RenameResult) {
        if (result.isSuccess()) {
            println("Renaming completed. ${result.renamedCount} files renamed.")
        } else {
            println("Renaming completed with errors:")
            result.errors.forEach { error ->
                println("  - $error")
            }
        }
    }
}