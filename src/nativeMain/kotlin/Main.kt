@file:OptIn(ExperimentalNativeApi::class)

package org.example

import org.example.app.App
import kotlin.experimental.ExperimentalNativeApi

fun main(args: Array<String>) {
    println("Hello, Kotlin/Native!")

    val platform = Platform.osFamily
    println("Running on: $platform")

    val app = App()
    app.run(args)
}