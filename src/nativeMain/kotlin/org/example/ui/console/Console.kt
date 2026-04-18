package org.example.ui.console

import org.example.domain.model.RenameResult

interface Console {
    fun println(message: String)
    fun print(message: String)
    fun readLine(): String?
    fun printRenameResult(result: RenameResult)
}