package com.example.playground

class MultiplyOperation : Calculator() {
    override fun operate(numbers: List<Int>): Int {
        val result = numbers.fold(1) { total, num -> total * num }
        println("곱셈 결과 >> $result")
        return result
    }
}