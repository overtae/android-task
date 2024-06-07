package com.example.playground

open class Calculator {
    open fun calculate(operation: AbstractOperation, x: Int, y: Int): Int {
        return operation.operate(x, y)
    }

    fun remain(x: Int, y: Int, isLoop: Boolean = true): Int {
        var result: Int
        try {
            result = x % y
            println("나머지 결과 >> $result")

        } catch (e: ArithmeticException) {
            result = -1
            println("0으로는 나눌 수 없습니다.")
        }
        return result.also { if (isLoop) loopCalculate(x, y) }
    }

    private fun loopCalculate(x: Int, y: Int) {
        while (true) {
            val type = getType()

            when (type) {
                -1 -> return println("종료합니다.")
                1 -> calculate(AddOperation(), x, y)
                2 -> calculate(SubtractOperation(), x, y)
                3 -> calculate(MultiplyOperation(), x, y)
                4 -> calculate(DivideOperation(), x, y)
                5 -> remain(x, y, false)
                else -> println("올바른 값을 입력해주세요.")
            }
        }
    }

    private fun getType(): Int {
        println(
            """계산을 선택해주세요. 그만하려면 -1을 입력해주세요.
            |1. 더하기 2. 빼기 3. 곱하기 4. 나누기 5. 나머지 구하기 """.trimMargin()
        )

        while (true) {
            readlnOrNull()?.let {
                try {
                    return it.toInt()
                } catch (e: NumberFormatException) {
                    println("숫자를 입력해주세요.")
                }
            }
        }
    }
}