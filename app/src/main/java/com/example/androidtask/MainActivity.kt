package com.example.androidtask

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.adapter.MainAdapter
import com.example.androidtask.data.Category
import com.example.androidtask.data.Header
import com.example.androidtask.data.ListDataType
import com.example.androidtask.data.ListDataWrapper
import com.example.androidtask.data.Live
import com.example.androidtask.data.Streamer

class MainActivity : AppCompatActivity() {
    val lgLiveList = arrayListOf(
        Live(
            2346,
            "방제 없음",
            "오리너구리9 · ",
            "리그 오브 레전드",
        ),
        Live(
            1280,
            "오늘 1등 못하면 접음",
            "파카 · ",
            "PUBG: 배틀 그라운드",
        ),
        Live(
            1024,
            "방구석 프로기의 코틀린 쯔뿌리기",
            "프로기 · ",
            "talk",
        ),
        Live(
            1,
            "안녕하세요",
            "이강진 · ",
            "talk",
        ),
    )
    val smLiveList = arrayListOf(
        Live(
            280,
            "아이언 두개재",
            "김태영",
            arrayListOf("", "", "")
        ),
        Live(
            280,
            "아이언 두개재",
            "김태일",
            arrayListOf("체인드 투게더", "", "")
        ),
        Live(
            280,
            "아이언 두개재",
            "김태이",
            arrayListOf("리그 오브 레전드", "", "")
        ),
        Live(
            280,
            "아이언 두개재",
            "김태삼",
            arrayListOf("", "", "")
        ),
        Live(
            280,
            "아이언 두개재",
            "김태사",
            arrayListOf("", "", "")
        ),
        Live(
            280,
            "아이언 두개재",
            "김태오",
            arrayListOf("", "", "")
        ),
    )
    val categoryList = arrayListOf(
        Category("talk", 185, 14000, R.drawable.img_category_talk),
        Category("PUBG: 배틀그라운드", 68, 4931, R.drawable.img_category_bg),
        Category("엘든 링 황금 나무의 그림자", 72, 4155, R.drawable.img_category_er, true),
        Category("로스트아크", 106, 2612, R.drawable.img_category_la),
        Category("종합 게임", 62, 2445, R.drawable.img_category_game),
        Category("리그 오브 레전드", 131, 2436, R.drawable.img_category_lol),
    )
    val streamerList = arrayListOf(
        arrayListOf(
            Streamer("스트리머 이름", R.drawable.img_anonymous, true),
            Streamer("스트리머 이름", R.drawable.img_anonymous, true),
            Streamer("스트리머 이름", R.drawable.img_anonymous, true),
            Streamer("스트리머 이름", R.drawable.img_anonymous, true),
            Streamer("스트리머 이름", R.drawable.img_anonymous, true)
        ),
        arrayListOf(
            Streamer("스트리머 이름", R.drawable.img_anonymous, true),
            Streamer("스트리머 이름", R.drawable.img_anonymous, true),
            Streamer("스트리머 이름", R.drawable.img_anonymous, true),
            Streamer("스트리머 이름", R.drawable.img_anonymous, true),
            Streamer("스트리머 이름", R.drawable.img_anonymous, true)
        ),
        arrayListOf(
            Streamer("스트리머 이름", R.drawable.img_anonymous, true),
            Streamer("스트리머 이름", R.drawable.img_anonymous, true),
            Streamer("스트리머 이름", R.drawable.img_anonymous, true),
            Streamer("스트리머 이름", R.drawable.img_anonymous, true),
            Streamer("스트리머 이름", R.drawable.img_anonymous, true)
        ),
    )


    private val mainItems: ArrayList<ListDataWrapper> = arrayListOf()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
    }

    private fun init() {
        initItems()

        val rvMain = findViewById<RecyclerView>(R.id.rv_main)
        mainAdapter = MainAdapter(mainItems)
        rvMain.adapter = mainAdapter
        rvMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initItems() {
        mainItems.add(ListDataWrapper(ListDataType.TYPE_LARGE_LIVE, lgLiveList))

        // small live list
        mainItems.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("이 방송 어때요?", ""))
        )
        smLiveList.map {
            mainItems.add(ListDataWrapper(ListDataType.TYPE_SMALL_LIVE, it))
        }

        // category list
        mainItems.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("좋아하실 것 같아요", ""))
        )
        mainItems.add(ListDataWrapper(ListDataType.TYPE_CATEGORY, categoryList))

        // medium live list
        mainItems.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("리그 오브 레전드 추천 라이브", "리그 오브 레전드"))
        )
        mainItems.add(ListDataWrapper(ListDataType.TYPE_MEDIUM_LIVE, smLiveList))

        // partner streamer list
        mainItems.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("파트너 스트리머", ""))
        )

        mainItems.add(ListDataWrapper(ListDataType.TYPE_STREAMER, streamerList))
    }
}