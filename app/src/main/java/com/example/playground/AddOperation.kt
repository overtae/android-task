package com.example.playground

class AddOperation : Calculator() {
    override fun operate(numbers: List<Int>): Int {
        val result = numbers.sum()
        println("덧셈 결과 >> $result")
        return result
    }
}