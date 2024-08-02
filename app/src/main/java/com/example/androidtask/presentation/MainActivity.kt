package com.example.androidtask.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.androidtask.R
import com.example.androidtask.data.viewmodel.BookmarkViewModel
import com.example.androidtask.data.viewmodel.ImageViewModel
import com.example.androidtask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val bookmarkViewModel by viewModels<BookmarkViewModel>()
    private val imageViewModel by viewModels<ImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
//        initViewModel()
    }

    private fun initViewModel() = with(imageViewModel) {
        searchResult.observe(this@MainActivity) { searchItems ->
            searchItems.forEach {
                it.isBookmarked = bookmarkViewModel.isBookmarked(it)
            }
            updateBookmarkState(searchItems)
        }
    }

    private fun initView() = with(binding) {
        replaceFragment(SearchFragment())
        bnMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_search -> {
                    replaceFragment(SearchFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.item_bookmark -> {
                    replaceFragment(BookmarkFragment())
                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .commit()
    }
}
