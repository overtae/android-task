package com.example.androidtask.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidtask.R
import com.example.androidtask.databinding.ActivityRecentSearchBinding

private const val SEARCH_TEXT = "search_text"
private const val RECENT_SEARCH = "recent_search"

class RecentSearchActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRecentSearchBinding.inflate(layoutInflater) }
    private val initialSearchText by lazy { intent.getStringExtra(SEARCH_TEXT) ?: "" }
    private val recentSearchList by lazy { loadSearchHistory() ?: hashSetOf() }
    private val recentSearchAdapter by lazy {
        RecentSearchAdapter(::handleDeleteRecentSearch, ::handleSubmitInput).apply {
            submitList(
                recentSearchList.toList()
            )
        }
    }

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
    }

    private fun initView() = with(binding) {
        rvRecentSearch.adapter = recentSearchAdapter
        ivPrev.setOnClickListener { finish() }
        ivCancel.setOnClickListener { etSearch.text.clear() }
        ivSearch.setOnClickListener {
            val searchText = etSearch.text.toString()
            val intent = Intent().apply { putExtra(SEARCH_TEXT, searchText) }
            setResult(RESULT_OK, intent)
            finish()
        }
        etSearch.setText(initialSearchText)
        etSearch.setOnEditorActionListener { textView, action, event ->
            val searchText = textView.text.toString()
            if (searchText.isNotEmpty()) ivCancel.visibility = View.VISIBLE
            else ivCancel.visibility = View.GONE

            // handle IME search event
            if (action == EditorInfo.IME_ACTION_SEARCH || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                if (searchText.isNotEmpty()) handleSubmitInput(searchText)
                else Toast.makeText(this@RecentSearchActivity, "검색어를 입력해주세요.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun handleSubmitInput(searchText: String) {
        recentSearchList.add(searchText)
        saveSearchHistory(recentSearchList)

        val intent = Intent().apply { putExtra(SEARCH_TEXT, searchText) }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun handleDeleteRecentSearch(item: String) {
        val newList = recentSearchList.filter { item != it }
        recentSearchList.clear()
        recentSearchList.addAll(newList)
        recentSearchAdapter.submitList(recentSearchList.toList())
        saveSearchHistory(recentSearchList)
    }

    private fun saveSearchHistory(set: Set<String>) {
        val sharedPref = getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        val edit = sharedPref.edit()

        edit.putStringSet(RECENT_SEARCH, set)
        edit.apply()
    }

    private fun loadSearchHistory(): HashSet<String>? {
        val sharedPref = getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        return sharedPref.getStringSet(RECENT_SEARCH, setOf<String>())?.toHashSet()
    }
}