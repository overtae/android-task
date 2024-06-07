package com.example.playground

class DivideOperation : AbstractOperation() {
    override fun operate(x: Int, y: Int): Int {
        try {
            val result = x / y
            println("나눗셈 결과 >> $result")
            return result
        } catch (e: ArithmeticException) {
            println("0으로는 나눌 수 없습니다.")
            return 0
        }
    }
}