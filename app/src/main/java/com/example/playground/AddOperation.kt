package com.example.playground

class AddOperation : AbstractOperation() {
    override fun operate(x: Int, y: Int): Int {
        return x + y
    }
}