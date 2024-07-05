package com.example.androidtask

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.adapter.MainAdapter
import com.example.androidtask.data.DataManager
import com.example.androidtask.data.Header
import com.example.androidtask.data.ListDataType
import com.example.androidtask.data.ListDataWrapper

class MainActivity : AppCompatActivity() {
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

        DataManager.init(this)
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
        mainItems.add(ListDataWrapper(ListDataType.TYPE_LARGE_LIVE, DataManager.lgLiveList))

        // small live list
        mainItems.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("이 방송 어때요?", ""))
        )
        DataManager.commonLiveList.map {
            mainItems.add(ListDataWrapper(ListDataType.TYPE_SMALL_LIVE, it))
        }

        // category list
        mainItems.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("좋아하실 것 같아요", ""))
        )
        mainItems.add(ListDataWrapper(ListDataType.TYPE_CATEGORY, DataManager.categoryList))

        // medium live list
        mainItems.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("리그 오브 레전드 추천 라이브", "리그 오브 레전드"))
        )
        mainItems.add(ListDataWrapper(ListDataType.TYPE_MEDIUM_LIVE, DataManager.commonLiveList))

        // partner streamer list
        mainItems.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("파트너 스트리머", ""))
        )
        mainItems.add(ListDataWrapper(ListDataType.TYPE_STREAMER, DataManager.streamerList))
    }
}