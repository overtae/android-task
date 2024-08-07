package com.example.androidtask.presentation.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidtask.databinding.FragmentBookmarkBinding
import com.example.androidtask.presentation.search.SearchAdapter
import com.example.androidtask.util.GridSpacingItemDecoration
import com.example.androidtask.util.px

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarkFragment : Fragment() {
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val bookmarkViewModel: BookmarkViewModel by activityViewModels {
        BookmarkViewModelFactory(requireContext())
    }
    private val searchAdapter by lazy { SearchAdapter { bookmarkViewModel.removeBookmark(it) } }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initViewModel() = with(bookmarkViewModel) {
        bookmarkList.observe(viewLifecycleOwner) {
            searchAdapter.submitList(it)
        }
    }

    private fun initView() = with(binding) {
        rvBookmark.run {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(GridSpacingItemDecoration(2, 16f.px))
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookmarkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookmarkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}