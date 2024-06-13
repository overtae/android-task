package com.example.playground

var menuList = ArrayList<Menu>()

fun main() {
    init()
    displayMenu()
    println("프로그램을 종료합니다.")
}

fun init() {
    val burgers = Menu("Burgers", "앵거스 비프 통살을 다져만든 버거")
    val frozenCustard = Menu("Frozen Custard", "매장에서 신선하게 만드는 아이스크림")
    val drinks = Menu("Drinks", "매장에서 직접 만드는 음료")
    val beer = Menu("Beer", "뉴욕 브루클린 브루어링에서 양조한 맥주")
    val menu = listOf(
        // main
        burgers, frozenCustard, drinks, beer,

        // burgers
        SubMenu(burgers.name, "ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"),
        SubMenu(burgers.name, "SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"),
        SubMenu(burgers.name, "Shroom Burger", 9.4, "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거"),
        SubMenu(burgers.name, "Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"),
        SubMenu(burgers.name, "Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거"),

        // frozenCustard
        SubMenu(frozenCustard.name, "Mango Shake", 7.8, "망고 퓨레와 바닐라 커스터드가 어우러진 쉐이크"),
        SubMenu(frozenCustard.name, "Classic Shakes", 6.8, "진한 커스터드가 들어간 클래식 쉐이크"),
        SubMenu(frozenCustard.name, "Floats", 6.8, "바닐라 커스터드와 탄산이 만난 음료"),
        SubMenu(frozenCustard.name, "Cup & Cones", 5.7, "쫀득하고 진한 아이스크림"),

        // drinks
        SubMenu(drinks.name, "Americano", 4.5, "물이 들어간 커피"),
        SubMenu(drinks.name, "Latte", 5.0, "우유를 넣은 커피"),
        SubMenu(drinks.name, "Caramel Latte", 5.9, "캬라멜 소스와 우유를 넣은 커피"),
        SubMenu(drinks.name, "Vanilla Latte", 5.5, "바닐라 시럽과 우유를 넣은 커피"),
        SubMenu(drinks.name, "Cappuccino", 5.5, "우유 거품이 들어간 커피"),

        // beer
        SubMenu(beer.name, "Cass", 6.5, "카스 생맥주"),
        SubMenu(beer.name, "Terra", 6.5, "테라 생맥주"),
        SubMenu(beer.name, "Krush", 7.0, "카리나가 광고한 맥주"),
        SubMenu(beer.name, "Asahi", 7.0, "아사히 생맥주"),
    )

    menu.map { menuList.add(it) }
}

fun displayMenu() {
    while (true) {
        val (food, menu) = menuList.partition { it is SubMenu }
        val maxMenuLength = menu.maxOfOrNull { it.name.length } ?: 0

        println("\"SHAKESHACK BURGER 에 오신걸 환영합니다.\"")
        println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.\n")
        println("[ SHAKESHACK MENU ]")
        menu.mapIndexed { i, item ->
            print("${i + 1}. ")
            item.displayInfo(maxMenuLength)
        }
        println("0. ${"종료".padEnd(maxMenuLength, ' ')} | 프로그램 종료\n")

        // 대분류 메뉴 입력
        val selectedMenuNumber = getInput(0..menu.size).also { if (it == 0) return }
        val selectedMenu = menu[selectedMenuNumber - 1]
        val filteredSubMenu = food.filter { (it as SubMenu).category == selectedMenu.name }
        val maxFoodLength = filteredSubMenu.maxOfOrNull { it.name.length } ?: 0

        println("[ ${selectedMenu.name} MENU ]")
        filteredSubMenu.mapIndexed { i, item ->
            print("${i + 1}. ")
            item.displayInfo(maxFoodLength)
        }
        println("0. ${"뒤로가기".padEnd(maxFoodLength, ' ')}| 뒤로가기\n")

        // 소분류 메뉴 입력
        val selectedFoodNumber = getInput(0..filteredSubMenu.size)

        if (selectedFoodNumber == 0) continue
        else {
            val selectedFood = filteredSubMenu[selectedFoodNumber - 1]
            println("선택하신 메뉴를 확인해주세요.")
            selectedFood.displayInfo(maxFoodLength)
            println()
        }
    }
}

fun getInput(range: IntRange): Int {
    while (true) {
        try {
            val input = readln().toInt()

            if (input in range) return input
            else println("잘못된 메뉴를 입력했어요. 다시 입력해주세요.")
        } catch (e: NumberFormatException) {
            println("숫자만 입력할 수 있어요. 메뉴 번호를 입력해주세요.")
        }
    }
}