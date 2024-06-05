package com.example.playground

class SubtractOperation : Calculator() {
    override fun operate(numbers: List<Int>): Int {
        val result = numbers.reduce { total, num -> total - num }
        println("뺄셈 결과 >> $result")
        return result
    }
}