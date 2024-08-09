package com.example.androidtask.presentation.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.databinding.FragmentSearchBinding
import com.example.androidtask.presentation.bookmark.BookmarkViewModel
import com.example.androidtask.presentation.bookmark.BookmarkViewModelFactory
import com.example.androidtask.presentation.data.ListItem
import com.example.androidtask.presentation.data.copy
import com.example.androidtask.presentation.data.find
import com.example.androidtask.presentation.data.isBookmarked
import com.example.androidtask.presentation.recent_search.RecentSearchActivity
import com.example.androidtask.util.GridSpacingItemDecoration
import com.example.androidtask.util.loadSearchHistory
import com.example.androidtask.util.px

private const val EXTRA_SEARCH_TEXT = "search_text"

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val recentSearchText by lazy {
        loadSearchHistory(requireContext()).lastOrNull() ?: ""
    }
    private val searchViewModel: SearchViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory()
    }
    private val bookmarkViewModel: BookmarkViewModel by activityViewModels {
        BookmarkViewModelFactory(requireContext())
    }
    private val searchAdapter by lazy {
        SearchAdapter { item ->
            if (item.isBookmarked) bookmarkViewModel.removeBookmark(item)
            else {
                item.copy(isBookmarked = true).also {
                    bookmarkViewModel.addBookmark(it)
                }
            }
        }
    }

    private var currentPage = 1
    private val searchResult = mutableListOf<ListItem>()
    private val loadingItem = ListItem.LoadingItem(true) as ListItem
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        searchViewModel.fetchSearchResult(recentSearchText)
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val searchText = result.data?.getStringExtra(EXTRA_SEARCH_TEXT)
                    binding.etSearch.setText(searchText)
                    handleSubmitInput()
                }
            }
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

    private fun initViewModel() {
        searchViewModel.searchResult.observe(viewLifecycleOwner) {
            searchResult.addAll(it)
            searchAdapter.submitList(listOf(*searchResult.toTypedArray(), loadingItem))
        }
        bookmarkViewModel.bookmarkList.observe(viewLifecycleOwner) { bookmarks ->
            searchResult.replaceAll { r ->
                when {
                    bookmarks.find(r) == null && r.isBookmarked -> r.copy(isBookmarked = false)
                    bookmarks.find(r) != null && !r.isBookmarked -> r.copy(isBookmarked = true)
                    else -> r
                }
            }
            searchAdapter.submitList(searchResult.toList())
        }
    }

    private fun initView() = with(binding) {
        etSearch.setText(recentSearchText)
        rvSearch.run {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(requireContext(), 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (searchAdapter.getItemViewType(position)) {
                            SearchAdapter.TYPE_LOADING -> 2
                            else -> 1
                        }
                    }
                }
            }
            addItemDecoration(GridSpacingItemDecoration(2, 16f.px))
        }
        etSearch.setOnClickListener {
            val intent = Intent(requireContext(), RecentSearchActivity::class.java).apply {
                putExtra(
                    EXTRA_SEARCH_TEXT,
                    etSearch.text.toString()
                )
            }
            activityResultLauncher.launch(intent)
        }
        fabToTop.setOnClickListener { rvSearch.smoothScrollToPosition(0) }
    }

    private fun handleSubmitInput() = with(binding) {
        val searchText = etSearch.text.toString()

        if (recentSearchText == searchText) return
        if (searchText.isNotEmpty()) {
            currentPage = 0
            searchResult.clear()
            fetchNextPage()
            return
        }
    }

    private fun fetchNextPage() = with(binding) {
        searchAdapter.stopLoading()
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
                // > 더이상 위로 스크롤 할 수 없거나 버튼이 보이고 아래로 스크롤 중일 때
                if (!recyclerView.canScrollVertically(-1) || (isVisible && dy > 0)) {
                    fabToTop.visibility = View.GONE
                    fabToTop.startAnimation(fadeOut)
                    isVisible = false
                }
                // > 버튼이 보이지 않고 위로 스크롤 중일 때
                else if (!isVisible && dy < 0) {
                    fabToTop.visibility = View.VISIBLE
                    fabToTop.startAnimation(fadeIn)
                    isVisible = true
                }
            }
        })
    }
}