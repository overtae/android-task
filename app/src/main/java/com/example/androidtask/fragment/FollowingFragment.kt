package com.example.androidtask.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.DetailActivity
import com.example.androidtask.LIVE
import com.example.androidtask.R
import com.example.androidtask.adapter.MainAdapter
import com.example.androidtask.data.DataManager
import com.example.androidtask.data.Header
import com.example.androidtask.data.ListDataType
import com.example.androidtask.data.ListDataWrapper

class FollowingFragment : Fragment() {
    private val listItem: ArrayList<ListDataWrapper> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListItems()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_list, container, false)
        val rvMain = view.findViewById<RecyclerView>(R.id.rv_main)

        val mainAdapter = MainAdapter(listItem) { live ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(LIVE, live)
            startActivity(intent)
        }
        rvMain.adapter = mainAdapter
        rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = FollowingFragment()
    }

    private fun initListItems() {
        // online streamer
        DataManager.commonLiveList.map {
            listItem.add(ListDataWrapper(ListDataType.TYPE_SMALL_LIVE, it))
        }

        // offline streamer
        listItem.add(ListDataWrapper(ListDataType.TYPE_HEADER, Header("오프라인", "")))
    }
}