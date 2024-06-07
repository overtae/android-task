package com.example.playground

class MultiplyOperation : AbstractOperation() {
    override fun operate(x: Int, y: Int): Int {
        val result = x * y
        println("곱셈 결과 >> $result")
        return result
    }
}