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
import com.example.androidtask.adapter.ViewPagerAdapter
import com.example.androidtask.data.DataManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

const val SEARCH_RESULT = "search"
const val LIVE = "live"

class MainActivity : AppCompatActivity() {
    private lateinit var getSearchResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }

        DataManager.init(this)

        val viewPager = findViewById<androidx.viewpager2.widget.ViewPager2>(R.id.vp_main)
        viewPager.adapter = ViewPagerAdapter(this)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_recommend)
                1 -> tab.text = getString(R.string.tab_following)
            }
        }.attach()

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
}