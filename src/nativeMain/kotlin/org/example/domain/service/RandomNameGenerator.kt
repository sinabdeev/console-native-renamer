package org.example.domain.service

import kotlin.random.Random

class RandomNameGenerator : NameGenerator {
    private val digits = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    private val colors = listOf("red", "blue", "green", "yellow", "purple", "orange", "pink", "brown", "black", "white")
    private val birds = listOf("eagle", "owl", "sparrow", "robin", "penguin", "parrot", "hawk", "swan", "peacock", "flamingo")

    override fun generateName(): String {
        val random = Random.Default
        
        // Генерация случайного имени из 3 частей: цифра + цвет + птица
        val digit = digits[random.nextInt(digits.size)]
        val color = colors[random.nextInt(colors.size)]
        val bird = birds[random.nextInt(birds.size)]
        
        return "$digit-$color-$bird"
    }
}