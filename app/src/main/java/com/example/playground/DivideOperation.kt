package com.example.playground

class DivideOperation : Calculator() {
    override fun operate(numbers: List<Int>): Int {
        try {
            val result = numbers.reduce { total, num -> total / num }
            println("나눗셈 결과 >> $result")
            return result
        } catch (e: ArithmeticException) {
            println("0으로는 나눌 수 없습니다.")
            return 0
        }
    }
}