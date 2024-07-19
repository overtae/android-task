package com.example.androidtask.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.activity.DetailActivity
import com.example.androidtask.activity.LIVE
import com.example.androidtask.R
import com.example.androidtask.adapter.MainAdapter
import com.example.androidtask.data.User
import com.example.androidtask.data.Header
import com.example.androidtask.data.ListDataType
import com.example.androidtask.data.ListDataWrapper

class FollowingFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = FollowingFragment()
    }

    private lateinit var mainAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    override fun onResume() {
        super.onResume()
        mainAdapter.submitList(getListItem().toList())
    }

    private fun initView(view: View) {
        val rvMain = view.findViewById<RecyclerView>(R.id.rv_main)
        mainAdapter = MainAdapter { live ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(LIVE, live)
            startActivity(intent)
        }.apply { submitList(getListItem()) }
        rvMain.adapter = mainAdapter
        rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun getListItem(): ArrayList<ListDataWrapper> {
        if (User.followingList.isEmpty()) return arrayListOf()
        val listItem: ArrayList<ListDataWrapper> = arrayListOf()

        // followed live list
        User.followingList.map {
            listItem.add(ListDataWrapper(ListDataType.TYPE_SMALL_LIVE, it))
        }

        // followed streamer list
        listItem.add(ListDataWrapper(ListDataType.TYPE_HEADER, Header("스트리머", "")))
        listItem.add(ListDataWrapper(ListDataType.TYPE_FOLLOWING, User.followingList.clone()))

        return listItem
    }
}