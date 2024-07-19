package com.example.androidtask.data

import android.content.Context
import com.example.androidtask.R

object DataManager {
    lateinit var lgLiveList: List<Live>
    lateinit var commonLiveList: List<Live>
    
    lateinit var categoryList: List<Category>
    lateinit var streamerList: List<List<Streamer>>

    fun init(context: Context) {
        context.initLgLive()
        initCommonLiveList()
        initCategoryList()
        initStreamerList()
    }

    private fun Context.initLgLive() {
        lgLiveList = listOf(
            Live(
                2346,
                "롤체 마스터 노방종",
                Streamer(
                    getString(R.string.streamer_name, "오리너구리9"),
                    R.drawable.img_profile_1,
                ),
                "01:24:31",
                "리그 오브 레전드",
                R.drawable.img_live_1,
                arrayListOf("리그 오브 레전드", "롤", "lol"),
                arrayListOf(
                    Supporter("태영이", 4.0),
                    Supporter("태일이", 2.3),
                    Supporter("태삼이", 1.0),
                ),
            ),
            Live(
                1280,
                "오늘 1등 못하면 접음(진짜임)",
                Streamer(
                    getString(R.string.streamer_name, "파카"),
                    R.drawable.img_profile_6,
                ),
                "01:24:31",
                "PUBG: 배틀 그라운드",
                R.drawable.img_live_2,
                arrayListOf("배틀 그라운드", "배그", ""),
                arrayListOf(
                    Supporter("큰손이야", 4.0),
                    Supporter("이등인데", 2.3),
                    Supporter("최고다", 1.0),
                ),
            ),
            Live(
                1024,
                "방구석 프로기의 코틀린 쯔뿌리기",
                Streamer(
                    getString(R.string.streamer_name, "프로기"),
                    R.drawable.img_profile_3,
                ),
                "01:24:31",
                "talk",
                R.drawable.img_live_3,
                arrayListOf("코틀린", "", ""),
                arrayListOf(
                    Supporter("고양이", 6.3),
                    Supporter("강아지", 4.8),
                ),
            ),
            Live(
                1,
                "꼼이 산책 브이로그",
                Streamer(
                    getString(R.string.streamer_name, "이강진"),
                    R.drawable.img_profile_4,
                ),
                "01:24:31",
                "talk",
                R.drawable.img_live_4,
                arrayListOf("브이로그", "", ""),
                arrayListOf(
                    Supporter("이강진", 0.1),
                ),
            ),
        )
    }

    private fun initCommonLiveList() {
        commonLiveList = listOf(
            Live(
                14587,
                "싱글 좀보이드",
                Streamer(
                    "한동숙",
                    R.drawable.img_profile_3,
                ),
                "01:24:31",
                "프로젝트 좀보이드",
                R.drawable.img_live_5,
                arrayListOf("프로젝트 좀보이드", "", ""),
            ),
            Live(
                8659,
                "닥터스톤 애니메이션 같이보기",
                Streamer(
                    "풍월량",
                    R.drawable.img_profile_2,
                ),
                "01:24:31",
                "닥터스톤",
                R.drawable.img_live_3,
                arrayListOf("talk", "종합게임", "신작")
            ),
            Live(
                13,
                "실음과 희망편 절망편 on",
                Streamer(
                    "사이테",
                    R.drawable.img_profile_4,
                ),
                "01:24:31",
                "",
                R.drawable.img_live_1,
                arrayListOf("talk", "실용음악", "작곡")
            ),
            Live(
                3390,
                "반갑습니다",
                Streamer(
                    "괴물쥐",
                    R.drawable.img_profile_5,
                ),
                "01:24:31",
                "",
                R.drawable.img_live_2,
                arrayListOf("리그 오브 레전드", "", "")
            ),
            Live(
                3641,
                "스빠룬키 가지광대5퀘스런 ㅇㅅㅇ",
                Streamer(
                    "녹두로",
                    R.drawable.img_profile_1,
                ),
                "01:24:31",
                "스팰렁키 2",
                R.drawable.img_live_4,
                arrayListOf("스펠렁키 2", "ㅇㅅㅇ", "녹두로")
            ),
            Live(
                280,
                "철면수심과 BIO INC.",
                Streamer(
                    "침착맨",
                    R.drawable.img_anonymous,
                ),
                "01:24:31",
                "",
                R.drawable.img_live_3,
                arrayListOf("", "", "")
            ),
        )
    }

    private fun initCategoryList() {
        categoryList = listOf(
            Category("talk", 742, 47000, R.drawable.img_category_talk),
            Category("PUBG: 배틀그라운드", 223, 8046, R.drawable.img_category_bg),
            Category("엘든 링 황금 나무의 그림자", 97, 2066, R.drawable.img_category_er, true),
            Category("로스트아크", 320, 4893, R.drawable.img_category_la),
            Category("종합 게임", 246, 1203, R.drawable.img_category_game),
            Category("리그 오브 레전드", 346, 10000, R.drawable.img_category_lol),
        )
    }

    private fun initStreamerList() {
        streamerList = listOf(
            listOf(
                Streamer("자동", R.drawable.img_profile_2, false),
                Streamer("시라유키 히나", R.drawable.img_profile_5, true),
                Streamer("인섹", R.drawable.img_profile_3, false),
                Streamer("아카네 리제", R.drawable.img_anonymous, true),
                Streamer("룩삼", R.drawable.img_profile_2, true)
            ),
            listOf(
                Streamer("똘똘똘이", R.drawable.img_profile_3, false),
                Streamer("아야츠노 유니", R.drawable.img_profile_4, true),
                Streamer("덜럭킹", R.drawable.img_profile_2, false),
                Streamer("김된모", R.drawable.img_profile_1, false),
                Streamer("괴물쥐", R.drawable.img_anonymous, true)
            ),
            listOf(
                Streamer("잭", R.drawable.img_profile_3, true),
                Streamer("빅헤드", R.drawable.img_profile_2, false),
                Streamer("꼴랑이", R.drawable.img_profile_4, true),
                Streamer("삐부", R.drawable.img_profile_1, true),
                Streamer("모카형", R.drawable.img_profile_5, true)
            )
        )
    }
}