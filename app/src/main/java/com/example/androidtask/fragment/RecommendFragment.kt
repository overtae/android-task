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
import com.example.androidtask.R
import com.example.androidtask.adapter.MainAdapter
import com.example.androidtask.data.DataManager
import com.example.androidtask.data.Header
import com.example.androidtask.data.ListDataType
import com.example.androidtask.data.ListDataWrapper
import com.example.androidtask.data.User

private const val LIVE = "live"

class RecommendFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = RecommendFragment()
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
        val listItem: ArrayList<ListDataWrapper> = arrayListOf()

        listItem.add(ListDataWrapper(ListDataType.TYPE_LARGE_LIVE, DataManager.lgLiveList))

        // following live list
        if (User.followingList.isNotEmpty()) {
            listItem.add(
                ListDataWrapper(ListDataType.TYPE_HEADER, Header("팔로잉 채널 라이브", ""))
            )
            listItem.add(ListDataWrapper(ListDataType.TYPE_MEDIUM_LIVE, User.followingList.clone()))
        }

        // small live list
        listItem.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("이 방송 어때요?", ""))
        )
        DataManager.commonLiveList.map {
            listItem.add(ListDataWrapper(ListDataType.TYPE_SMALL_LIVE, it))
        }

        // category list
        listItem.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("좋아하실 것 같아요", ""))
        )
        listItem.add(ListDataWrapper(ListDataType.TYPE_CATEGORY, DataManager.categoryList))

        // medium live list
        listItem.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("리그 오브 레전드 추천 라이브", "리그 오브 레전드"))
        )
        listItem.add(
            ListDataWrapper(
                ListDataType.TYPE_MEDIUM_LIVE,
                DataManager.commonLiveList
            )
        )

        // partner streamer list
        listItem.add(
            ListDataWrapper(ListDataType.TYPE_HEADER, Header("파트너 스트리머", ""))
        )
        listItem.add(ListDataWrapper(ListDataType.TYPE_STREAMER, DataManager.streamerList))

        return listItem
    }
}