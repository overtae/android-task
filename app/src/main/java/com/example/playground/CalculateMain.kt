package com.example.playground

fun main() {
    val calculator = Calculator()
    val numbers = getNumberList()

    // Add
    val addRes = calculator.calculate(AddOperation(), numbers[0], numbers[1])
    println(addRes + 10)

    // Subtract
    val subRes = calculator.calculate(SubtractOperation(), numbers[0], numbers[1])
    println(subRes + 10)

    // Multiply
    val mulRes = calculator.calculate(MultiplyOperation(), numbers[0], numbers[1])
    println(mulRes + 10)

    // Divide
    val divRes = calculator.calculate(DivideOperation(), numbers[0], numbers[1])
    println(divRes + 10)

    // Remain
    val remainRes = calculator.remain(numbers[0], numbers[1])
    println(remainRes + 10)
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