package com.example.playground

class Calculator {
    fun add(x: Int, y: Int): Int {
        var result = x + y
        println(result)
        return result
    }

    fun sub(x: Int, y: Int): Int {
        var result = x - y
        println(result)
        return result
    }

    fun mul(x: Int, y: Int): Int {
        var result = x * y
        println(result)
        return result
    }

    fun divide(x: Int, y: Int): Int {
        var result = x / y
        println(if (y != 0) result else "0으로는 나눌 수 없습니다.")
        return if (y != 0) result else -1
    }

    fun remain(x: Int, y: Int, isLoop: Boolean = true) {
        var result = x % y
        println(if (y != 0) result else "0으로는 나눌 수 없습니다.")
        if (isLoop) loopCalculate(x, y)
    }

    private fun loopCalculate(x: Int, y: Int) {
        do {
            val type = getType()

            when (type) {
                -1 -> return println("종료합니다.")
                1 -> add(x, y)
                2 -> sub(x, y)
                3 -> mul(x, y)
                4 -> divide(x, y)
                5 -> remain(x, y, false)
                else -> println("올바른 값을 입력해주세요.")
            }
        } while (true)
    }

    private fun getType(): Int {
        println(
            """계산을 선택해주세요. 그만하려면 -1을 입력해주세요.
                |1. 더하기 2. 빼기 3. 곱하기 4. 나누기 5. 나머지 구하기 """.trimMargin()
        )

        do {
            readlnOrNull()?.let {
                when {
                    it.toInt() != null -> return it.toInt()
                    else -> println("숫자를 입력해주세요.")
                }
            }
        } while (true)
    }
}