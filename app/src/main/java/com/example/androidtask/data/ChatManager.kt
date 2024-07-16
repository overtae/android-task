package com.example.androidtask.data

import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

object ChatManager {
    val chatList = arrayListOf<Chat>()

    val userNameDummy = listOf(
        "하울의음쥑이는성",
        "이웃집또털어",
        "피부암통키",
        "니이모를찾아서",
        "전국모래자랑",
        "뱃살공주",
        "폭행몬스터",
        "낄낄몬스터",
        "강아지맛쥬스",
        "오리너구리9",
        "거져줄게잘사가",
        "벼랑위에당뇨",
        "신밧드의보험",
        "티끌모아파산",
        "여자라서햄볶아요",
        "톱과젤리",
        "천국의계란",
        "순대렐라",
        "곰탕재료푸우",
        "반만고양이"
    )
    val contentDummy = listOf(
        "벌써 가을인가봐요",
        "퇴근 goat",
        "아이템은 안 먹어요?",
        "ㅋㅋㅋㅋㅋ",
        "!논란",
        "귀엽더라고요",
        "캬~",
        "오",
        "도파민 방송 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ",
        "와 퇴근이다",
        "ㅊㅊㅊㅊㅊㅊㅊㅊㅊㅊ",
        "?",
        "ㄷㄷ..",
        "아니 그거 그렇게 하는 거 아닌데",
        "ㄹㅇㅋㅋ",
        "ㅇㅇ",
        "네",
        "ㅇㅈ",
        "쓰읍...",
        "흠",
        "선생님 졸려요",
        "맞아요",
        "ㅔ",
        "ㄴㄴㄴㄴ"
    )

    fun startChat(onChat: () -> Unit): Timer {
        chatList.add(Chat(userNameDummy.random(), contentDummy.random()))
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (Random.nextBoolean()) chatList.add(
                    Chat(
                        userNameDummy.random(),
                        contentDummy.random()
                    )
                )
                onChat()
            }
        }, 0, 1000)
        return timer
    }

    fun addChat(chat: String, onChat: () -> Unit) {
        chatList.add(Chat(userNameDummy.random(), chat))
        onChat()
    }

    fun clearChat(onClear: () -> Unit) {
        chatList.clear()
        onClear()
    }
}