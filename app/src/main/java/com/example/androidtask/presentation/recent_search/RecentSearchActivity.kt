package com.example.androidtask.presentation.recent_search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.google.gson.Gson

private const val SEARCH_TEXT = "search_text"
private const val RECENT_SEARCH = "recent_search"

class RecentSearchActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRecentSearchBinding.inflate(layoutInflater) }
    private val initialSearchText by lazy { intent.getStringExtra(SEARCH_TEXT) ?: "" }
    private val recentSearchList: ArrayList<String> by lazy {
        loadSearchHistory(this).toCollection(
            ArrayList()
        )
    }
    private val recentSearchAdapter by lazy {
        RecentSearchAdapter(::handleDeleteRecentSearch, ::handleSubmitInput).apply {
            submitList(recentSearchList.toList())
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
        ivSearch.setOnClickListener { handleSubmitInput(etSearch.text.toString()) }
        ivCancel.apply {
            visibility = if (initialSearchText.isNotEmpty()) View.VISIBLE else View.GONE
            setOnClickListener { etSearch.text.clear() }
        }
        etSearch.apply {
            setText(initialSearchText)
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    if (etSearch.text.toString().isNotEmpty()) ivCancel.visibility = View.VISIBLE
                    else ivCancel.visibility = View.GONE
                }
            })
            setOnEditorActionListener { textView, action, event ->
                if (action == EditorInfo.IME_ACTION_SEARCH || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    handleSubmitInput(textView.text.toString())
                    return@setOnEditorActionListener true
                }
                false
            }
        }
    }

    private fun handleSubmitInput(item: String) {
        if (item.isNotBlank()) {
            val newList = listOf(*recentSearchList.filter { it != item }.toTypedArray(), item)
            recentSearchAdapter.submitList(newList.toList())
            recentSearchList.clear().also { recentSearchList.addAll(newList) }
            saveSearchHistory(this, newList)

            val intent = Intent().apply { putExtra(SEARCH_TEXT, item) }
            setResult(RESULT_OK, intent)
            finish()
        } else Toast.makeText(this, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
    }

    private fun handleDeleteRecentSearch(item: String) {
        val newList = recentSearchList.filter { it != item }
        recentSearchAdapter.submitList(newList.toList())
        recentSearchList.clear().also { recentSearchList.addAll(newList) }
        saveSearchHistory(this, recentSearchList)
    }
}

fun saveSearchHistory(context: Context, item: List<String>) {
    val gson = Gson()
    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )
    val edit = sharedPref.edit()
    edit.putString(RECENT_SEARCH, gson.toJson(item))
    edit.apply()
}

fun loadSearchHistory(context: Context): List<String> {
    val gson = Gson()
    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )
    val json = sharedPref.getString(RECENT_SEARCH, "")
    return gson.fromJson(json, Array<String>::class.java)?.toList() ?: listOf()
}