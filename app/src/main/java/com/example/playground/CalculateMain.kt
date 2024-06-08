package com.example.playground

fun main() {
    val add = Calculator(AddOperation())
    val sub = Calculator(SubtractOperation())
    val mul = Calculator(MultiplyOperation())
    val div = Calculator(DivideOperation())

    fun loopCalculate(x: Int, y: Int) {
        while (true) {
            val type = getType()

            when (type) {
                -1 -> return println("종료합니다.")
                1 -> add.calculate(x, y)
                2 -> sub.calculate(x, y)
                3 -> mul.calculate(x, y)
                4 -> div.calculate(x, y)
                5 -> add.remain(x, y)
                else -> println("올바른 값을 입력해주세요.")
            }
        }
    }

    val numbers = getNumberList()

    // Add
    val addRes = add.calculate(numbers[0], numbers[1])
    println("덧셈 결과 >> $addRes")

    // Subtract
    val subRes = sub.calculate(numbers[0], numbers[1])
    println("뺄셈 결과 >> $subRes")

    // Multiply
    val mulRes = mul.calculate(numbers[0], numbers[1])
    println("곱셈 결과 >> $mulRes")

    // Divide
    val divRes = div.calculate(numbers[0], numbers[1])
    println("나눗셈 결과 >> $divRes")

    // Remain
    val remainRes = add.remain(numbers[0], numbers[1], ::loopCalculate)
    println("나머지 결과 >> $remainRes")
}

fun getNumberList(): List<Int> {
    println("계산하실 숫자를 공백으로 구분하여 입력해주세요.")

    while (true) {
        readlnOrNull()?.let {
            try {
                return it.split(" ").map(String::toInt)
            } catch (e: NumberFormatException) {
                println("올바른 값을 입력해주세요.")
            }
        }
    }
}

fun getType(): Int {
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
