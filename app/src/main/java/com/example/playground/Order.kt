package com.example.playground

import kotlin.math.round

class Order(private var money: Double) {
    val cart = ArrayList<SubMenu>()
    private var total = 0.0
    private var isBuyable = total <= money

    init {
        money = round(money * 10) / 10
    }

    fun displayCart() {
        val maxCartLength = cart.maxOfOrNull { it.name.length } ?: 0

        println("[ Orders ]")
        cart.map { it.displayInfo(maxCartLength) }
        println("\n[ Total ]")
        println("W $total (잔액: W $money)")
    }

    fun addToCart(item: SubMenu) {
        cart.add(item)
        total += item.price
        updateBuyableState()
        println("${item.name} 가 장바구니에 추가되었습니다.\n")
    }

    fun clearCart() {
        cart.clear()
        total = 0.0
        updateBuyableState()
        println("장바구니를 비웠습니다.\n")
    }

    fun purchase() {
        if (isBuyable) {
            money -= total
            total = 0.0
            cart.clear()

            println("주문이 완료되었습니다.")
            println("현재 잔액은 ${money}W 입니다.\n")
        } else {
            println("현재 잔액은 ${money}W 으로 ${total - money}W이 부족해서 주문할 수 없습니다.")
        }
    }

    private fun updateBuyableState() {
        isBuyable = total <= money
    }

    companion object {
        var waiting = 0
    }
}