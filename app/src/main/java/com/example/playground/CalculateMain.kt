package com.example.playground

fun main() {
    val calculator = Calculator()

    println("1 + 2 = ${calculator.add(1, 2)}")
    println("1 - 2 = ${calculator.sub(1, 2)}")
    println("1 * 2 = ${calculator.mul(1, 2)}")
    println("1 / 2 = ${calculator.divide(1, 2)}")
    println("1 / 0 = ${calculator.divide(1, 0)}")
}