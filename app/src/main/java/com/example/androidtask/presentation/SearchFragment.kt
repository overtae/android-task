package com.example.androidtask.presentation

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.data.viewmodel.BookmarkViewModel
import com.example.androidtask.data.viewmodel.BookmarkViewModelFactory
import com.example.androidtask.data.viewmodel.SearchViewModel
import com.example.androidtask.databinding.FragmentSearchBinding
import com.example.androidtask.util.GridSpacingItemDecoration
import com.example.androidtask.util.hideKeyBoard
import com.example.androidtask.util.px

private const val SEARCH_HISTORY = "search_history"

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchSearchResult(loadSearchHistory() ?: "")
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
        fetchNextPage()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initViewModel() = with(viewModel) {
        searchResult.observe(viewLifecycleOwner) {
            val hasLoadingItem = it.count { item -> item is ListItem.LoadingItem } > 0
            mainAdapter.submitList(if (hasLoadingItem) it else it.plus(ListItem.LoadingItem(true)))
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
    }

    private fun handleSubmitInput() = with(binding) {
        val searchText = etSearch.text.toString()

        requireContext().hideKeyBoard(etSearch)
        if (searchText.isNotEmpty()) {
            viewModel.fetchSearchResult(etSearch.text.toString())
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
        rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter?.itemCount ?: 0

                if (!rvSearch.canScrollVertically(1) && lastVisibleItemPosition >= itemTotalCount - 1) {
                    mainAdapter.stopLoading()
                    rvSearch.smoothScrollToPosition(itemTotalCount - 1)
                    viewModel.fetchSearchResult(etSearch.text.toString(), ++currentPage)
                }
            }
        })
    }
}