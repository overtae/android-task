package com.example.androidtask.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidtask.fragment.FollowingFragment
import com.example.androidtask.fragment.RecommendFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int) = when (position) {
        0 -> RecommendFragment.newInstance()
        else -> FollowingFragment.newInstance()
    }
}