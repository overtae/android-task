package com.example.playground

open class Menu(val name: String, val description: String) {
    open fun displayInfo(formatLength: Int) {
        println("${name.padEnd(formatLength, ' ')}   | $description")
    }
}