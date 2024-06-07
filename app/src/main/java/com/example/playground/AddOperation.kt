package com.example.playground

class AddOperation : AbstractOperation() {
    override fun operate(x: Int, y: Int): Int {
        val result = x + y
        println("덧셈 결과 >> $result")
        return result
    }
}