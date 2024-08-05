package com.example.androidtask.presentation

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.presentation.viewmodel.BookmarkViewModel
import com.example.androidtask.presentation.viewmodel.BookmarkViewModelFactory
import com.example.androidtask.presentation.viewmodel.SearchViewModel
import com.example.androidtask.databinding.FragmentSearchBinding
import com.example.androidtask.presentation.viewmodel.SearchViewModelFactory
import com.example.androidtask.util.GridSpacingItemDecoration
import com.example.androidtask.util.hideKeyBoard
import com.example.androidtask.util.px

private const val SEARCH_HISTORY = "search_history"

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory()
    }
    private val bookmarkViewModel: BookmarkViewModel by activityViewModels {
        BookmarkViewModelFactory(requireContext())
    }
    private val mainAdapter by lazy {
        MainAdapter { item ->
            if (bookmarkViewModel.isBookmarked(item)) bookmarkViewModel.removeBookmark(item)
            else bookmarkViewModel.addBookmark(item)
        }
    }

    private var currentPage = 1
    private val result = mutableListOf<ListItem>()
    private val loadingItem = ListItem.LoadingItem(true) as ListItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel.fetchSearchResult(loadSearchHistory() ?: "")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        initScrollListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initViewModel() = with(searchViewModel) {
        searchResult.observe(viewLifecycleOwner) {
            result.addAll(it)
            mainAdapter.submitList(listOf(*result.toTypedArray(), loadingItem))
        }
    }

    private fun initView() = with(binding) {
        etSearch.setText(loadSearchHistory())
        rvSearch.run {
            adapter = mainAdapter
            layoutManager = GridLayoutManager(requireContext(), 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (mainAdapter.getItemViewType(position)) {
                            MainAdapter.TYPE_LOADING -> 2
                            else -> 1
                        }
                    }
                }
            }
            addItemDecoration(GridSpacingItemDecoration(2, 16f.px))
        }
        btnSearch.setOnClickListener { handleSubmitInput() }
        etSearch.setOnEditorActionListener { _, action, event ->
            if (action == EditorInfo.IME_ACTION_SEARCH || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                handleSubmitInput()
                return@setOnEditorActionListener true
            }
            false
        }
        fabToTop.setOnClickListener { rvSearch.smoothScrollToPosition(0) }
    }

    private fun handleSubmitInput() = with(binding) {
        val searchText = etSearch.text.toString()

        requireContext().hideKeyBoard(etSearch)
        if (loadSearchHistory() == searchText) return
        if (searchText.isNotEmpty()) {
            currentPage = 0
            result.clear()
            fetchNextPage()
            saveSearchHistory(searchText)
            return
        }
        Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
    }

    private fun saveSearchHistory(searchText: String) {
        val sharedPref = requireContext().getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        val edit = sharedPref.edit()

        edit.putString(SEARCH_HISTORY, searchText)
        edit.apply()
    }

    private fun loadSearchHistory(): String? {
        val sharedPref = requireContext().getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        return sharedPref.getString(SEARCH_HISTORY, "")
    }

    private fun fetchNextPage() = with(binding) {
        mainAdapter.stopLoading()
        searchViewModel.fetchSearchResult(etSearch.text.toString(), ++currentPage)
    }

    private fun initScrollListener() = with(binding) {
        rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var isVisible = false
            val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 200 }
            val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 200 }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // infinite scroll
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter?.itemCount ?: 0

                if (!rvSearch.canScrollVertically(1) && lastVisibleItemPosition >= itemTotalCount - 1) {
                    fetchNextPage()
                }

                // scroll to top
                // 더이상 위로 스크롤 할 수 없거나 버튼이 보이고 아래로 스크롤 중일 때
                if (!recyclerView.canScrollVertically(-1) || (isVisible && dy > 0)) {
                    fabToTop.visibility = View.GONE
                    fabToTop.startAnimation(fadeOut)
                    isVisible = false
                }

                // 버튼이 보이지 않고 위로 스크롤 중일 때
                else if (!isVisible && dy < 0) {
                    fabToTop.visibility = View.VISIBLE
                    fabToTop.startAnimation(fadeIn)
                    isVisible = true
                }
            }
        })
    }
}