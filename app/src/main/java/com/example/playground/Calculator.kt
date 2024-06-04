package com.example.playground

class Calculator {
    fun add(x: Int, y: Int): Int {
        return x + y
    }

    fun sub(x: Int, y: Int): Int {
        return x - y
    }

    fun mul(x: Int, y: Int): Int {
        return x * y
    }

    fun divide(x: Int, y: Int): Int {
        return if (y != 0) x / y else -1
    }
}