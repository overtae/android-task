package com.example.androidtask.presentation.main

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidtask.presentation.bookmark.BookmarkFragment
import com.example.androidtask.presentation.search.SearchFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when (position) {
        0 -> SearchFragment()
        else -> BookmarkFragment()
    }
}