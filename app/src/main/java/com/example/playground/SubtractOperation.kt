package com.example.playground

class SubtractOperation : AbstractOperation() {
    override fun operate(x: Int, y: Int): Int {
        val result = x - y
        println("뺄셈 결과 >> $result")
        return result
    }
}