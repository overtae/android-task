package com.example.playground

fun main() {
    val calculator = Calculator()

    println(calculator.add(1, 2) + calculator.sub(3, 4))

    println("계산하실 숫자를 공백으로 구분하여 입력해주세요.")
    val numbers = getInput().split(" ").map(String::toInt)

    calculator.remain(numbers[0], numbers[1])
}

fun getInput(): String {
    do {
        readlnOrNull()?.let {
            when {
                it.replace(" ", "").toInt() != null -> return it
                else -> println("올바른 값을 입력해주세요.")
            }
        }
    } while (true)
}