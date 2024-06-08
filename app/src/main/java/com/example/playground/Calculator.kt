package com.example.playground

class Calculator(private val operation: AbstractOperation) {
    fun calculate(x: Int, y: Int): Int {
        return operation.operate(x, y)
    }

    fun remain(x: Int, y: Int, loopFun: ((Int, Int) -> Unit)? = null): Int {
        var result: Int
        try {
            result = x % y
            println("나머지 결과 >> $result")

        } catch (e: ArithmeticException) {
            result = -1
            println("0으로는 나눌 수 없습니다.")
        }
        return result.also { if (loopFun != null) loopFun(x, y) }
    }
}