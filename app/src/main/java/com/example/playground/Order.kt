package com.example.playground

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.round

class Order(private var money: Double) {
    val cart = ArrayList<SubMenu>()
    private var total = 0.0
    private var isBuyable = total <= money

    init {
        money = round(money * 10) / 10
    }

    fun displayOrderMenu() {
        val maxCartLength = cart.maxOfOrNull { it.name.length } ?: 0

        println("\n아래와 같이 주문 하시겠습니까? (현재 주문 대기수: ${waiting})\n")
        println("[ Orders ]")
        cart.map { it.displayInfo(maxCartLength) }
        println("\n[ Total ]")
        println("W $total (잔액: W $money)")
        println("1. 주문      2. 메뉴판\n")

        val input = getInput(1..2)
        if (input == 1) purchase()
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
        waiting = 0
        updateBuyableState()
        println("장바구니를 비웠습니다.\n")
    }

    private fun purchase() {
        val timeFormmatter = DateTimeFormatter.ofPattern("a HH시 mm분").withLocale(Locale.KOREA)
        val datetimeFormmatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd일 HH:mm:ss").withLocale(Locale.KOREA)

        val current = LocalDateTime.now()
        val currentTime = current.format(timeFormmatter)
        val currentDate = current.format(datetimeFormmatter)

        if (isBuyable) {
            if ((current.toLocalTime()) in noPurchaseTimeFrom..noPurchaseTimeTo) {
                println("\n현재 시각은 ${currentTime}입니다.")
                println(
                    "은행 점검 시간은 ${noPurchaseTimeFrom.format(timeFormmatter)} ~ ${
                        noPurchaseTimeTo.format(
                            timeFormmatter
                        )
                    }이므로 결제할 수 없습니다.\n"
                )
                waiting += 1
                return
            }

            waiting = if (waiting == 0) 0 else waiting - 1
            money -= total
            total = 0.0
            cart.clear()

            println("결제를 완료했습니다. ($currentDate)")
            println("현재 잔액은 ${money}W 입니다.\n")
        } else {
            val diff = round((total - money) * 10) / 10
            println("현재 잔액은 ${money}W 으로 ${diff}W이 부족해서 주문할 수 없습니다.")
        }
    }

    private fun updateBuyableState() {
        isBuyable = total <= money
    }

    companion object {
        @Volatile
        private var waiting = 0

        private val noPurchaseTimeFrom = LocalTime.of(1, 15)
        private val noPurchaseTimeTo = LocalTime.of(23, 1)

        fun displayWaiting() {
            println("현재 주문 대기수: $waiting")
        }
    }
}