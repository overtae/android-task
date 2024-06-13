package com.example.playground

class Food(val category: String, name: String, val price: Double, description: String) :
    Menu(name, description) {
    override fun displayInfo(formatLength: Int) {
        println("${name.padEnd(formatLength, ' ')}   | w $price | $description")
    }
}