@file:OptIn(ExperimentalNativeApi::class)

package org.example

import kotlin.experimental.ExperimentalNativeApi

fun main() {
    println("Hello, Kotlin/Native!")


    val platform = Platform.osFamily
    println("Running on: $platform")

    main2()

}