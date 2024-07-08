package com.example.androidtask

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.material.tabs.TabLayout

const val SEARCH_RESULT = "search"

class MainActivity : AppCompatActivity() {
    private val suggestionItems: ArrayList<ListDataWrapper> = arrayListOf()
    private val followingItems: ArrayList<ListDataWrapper> = arrayListOf()
    private lateinit var mainAdapter: MainAdapter
    private lateinit var getSearchResult: ActivityResultLauncher<Intent>

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
        initSuggestionItems()
        initFollowingItems()
        init()

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> init(suggestionItems)
                    1 -> init(followingItems)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        getSearchResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val searchText = it.data?.getStringExtra(SEARCH_RESULT) ?: ""
                    Toast.makeText(this@MainActivity, "검색어: $searchText", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this@MainActivity, "검색어를 가져오지 못했습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        findViewById<ImageButton>(R.id.btn_search).setOnClickListener {
            getSearchResult.launch(Intent(this@MainActivity, SearchActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btn_profile).setOnClickListener {
            startActivity(Intent(this@MainActivity, UploadActivity::class.java))
        }
    }

    private fun init(item: ArrayList<ListDataWrapper> = suggestionItems) {
        val rvMain = findViewById<RecyclerView>(R.id.rv_main)
        mainAdapter = MainAdapter(item)
        rvMain.adapter = mainAdapter
        rvMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initSuggestionItems() {
        suggestionItems.add(ListDataWrapper(ListDataType.TYPE_LARGE_LIVE, DataManager.lgLiveList))

        // small live list
        suggestionItems.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("이 방송 어때요?", ""))
        )
        DataManager.commonLiveList.map {
            suggestionItems.add(ListDataWrapper(ListDataType.TYPE_SMALL_LIVE, it))
        }

        // category list
        suggestionItems.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("좋아하실 것 같아요", ""))
        )
        suggestionItems.add(ListDataWrapper(ListDataType.TYPE_CATEGORY, DataManager.categoryList))

        // medium live list
        suggestionItems.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("리그 오브 레전드 추천 라이브", "리그 오브 레전드"))
        )
        suggestionItems.add(
            ListDataWrapper(
                ListDataType.TYPE_MEDIUM_LIVE,
                DataManager.commonLiveList
            )
        )

        // partner streamer list
        suggestionItems.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("파트너 스트리머", ""))
        )
        suggestionItems.add(ListDataWrapper(ListDataType.TYPE_STREAMER, DataManager.streamerList))
    }

    private fun initFollowingItems() {
        // online streamer
        DataManager.commonLiveList.map {
            followingItems.add(ListDataWrapper(ListDataType.TYPE_SMALL_LIVE, it))
        }

        // offline streamer
        followingItems.add(ListDataWrapper(ListDataType.TYPE_HEADER, Header("오프라인", "")))
    }
}