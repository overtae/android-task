package com.example.playground

fun main() {
    val numbers = getNumberList()

    // Add
    val addRes = AddOperation().operate(numbers)
    println(addRes + 10)

    // Subtract
    val subRes = SubtractOperation().operate(numbers)
    println(subRes + 10)

    // Multiply
    val mulRes = MultiplyOperation().operate(numbers)
    println(mulRes + 10)

    // Divide
    val divRes = DivideOperation().operate(numbers)
    println(divRes + 10)
}

fun getNumberList(): List<Int> {
    println("계산하실 숫자를 공백으로 구분하여 입력해주세요.")

    do {
        readlnOrNull()?.let {
            when {
                it.replace(" ", "").toIntOrNull() != null -> return it.split(" ").map(String::toInt)
                else -> println("올바른 값을 입력해주세요.")
            }
        }
    } while (true)
}