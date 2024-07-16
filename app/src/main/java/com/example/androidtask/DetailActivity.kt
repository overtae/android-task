package com.example.androidtask

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.adapter.ChatAdapter
import com.example.androidtask.data.ChatManager
import com.example.androidtask.data.Live
import com.example.androidtask.holder.formatNumber
import java.util.Timer

class DetailActivity : AppCompatActivity() {
    private lateinit var timer: Timer
    private val live by lazy { intent.getParcelableExtra(LIVE, Live::class.java) }
    private val chatList by lazy { ChatManager.chatList }
    private val adapter by lazy { ChatAdapter().apply { submitList(chatList) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
        handleInputChat()

        val rvChat = findViewById<RecyclerView>(R.id.rv_chat)
        rvChat.adapter = adapter
        rvChat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        timer = ChatManager.startChat {
            adapter.submitList(chatList.toList())
            rvChat.smoothScrollToPosition(chatList.size - 1)
        }

        val etChat = findViewById<EditText>(R.id.et_chat)
        etChat.setOnEditorActionListener { et, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEND) {
                val inputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(et.windowToken, 0)
                ChatManager.addChat(et.text.toString()) {
                    adapter.submitList(chatList.toList())
                    rvChat.smoothScrollToPosition(chatList.size - 1)
                    et.text = ""
                }
            }
            true
        }
    }

    private fun handleInputChat() {
        val target = findViewById<LinearLayout>(R.id.layout_chat)
        val rootView = findViewById<View>(android.R.id.content)
        rootView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            private var isKeyboardShown = false

            override fun onGlobalLayout() {
                val r = Rect()
                rootView.getWindowVisibleDisplayFrame(r)
                val heightDiff = rootView.bottom - r.bottom

                isKeyboardShown = heightDiff > 100
                if (isKeyboardShown) {
                    target.y = r.bottom - 160f
                } else {
                    target.y = rootView.height - 270f
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        ChatManager.clearChat { adapter.submitList(chatList.toList()) }
    }

    private fun init() {
        live?.let {
            findViewById<ImageView>(R.id.iv_close).setOnClickListener { finish() }

            findViewById<ImageView>(R.id.iv_live_screen).setImageResource(it.screenImg)
            findViewById<ImageView>(R.id.iv_streamer_profile).setImageResource(it.streamer.profileImg)

            findViewById<TextView>(R.id.tv_live_title).text = it.title
            findViewById<TextView>(R.id.tv_live_streamer).text = it.streamer.name

            findViewById<TextView>(R.id.tv_live_viewer).text =
                getString(R.string.live_viewer, it.viewer.formatNumber())
            findViewById<TextView>(R.id.tv_live_time).text =
                getString(R.string.live_time, it.liveTime)

            findViewById<TextView>(R.id.tv_live_game).apply {
                if (it.game.isNotEmpty()) text = it.game
                else visibility = View.GONE
            }

            it.tags.mapIndexed { i, tag ->
                when (i) {
                    0 -> findViewById<TextView>(R.id.tv_live_tag_1)
                    1 -> findViewById(R.id.tv_live_tag_2)
                    else -> findViewById(R.id.tv_live_tag_3)
                }.apply {
                    if (tag.isEmpty()) visibility = View.GONE
                    else text = tag
                }
            }

            (0..2).map { i ->
                when (i) {
                    0 -> listOf(
                        findViewById<TextView>(R.id.tv_supporter_1),
                        findViewById<TextView>(R.id.tv_cheese_1),
                        findViewById<ImageView>(R.id.iv_cheese_1),
                        findViewById<ImageView>(R.id.iv_supporter_1)
                    )

                    1 -> listOf(
                        findViewById<TextView>(R.id.tv_supporter_2),
                        findViewById<TextView>(R.id.tv_cheese_2),
                        findViewById<ImageView>(R.id.iv_cheese_2),
                        findViewById<ImageView>(R.id.iv_supporter_2)
                    )

                    else -> listOf(
                        findViewById<TextView>(R.id.tv_supporter_3),
                        findViewById<TextView>(R.id.tv_cheese_3),
                        findViewById<ImageView>(R.id.iv_cheese_3),
                        findViewById<ImageView>(R.id.iv_supporter_3)
                    )
                }.apply {
                    if (i < it.supporters.size) {
                        (this[0] as TextView).text = it.supporters[i].name
                        (this[1] as TextView).text =
                            getString(R.string.live_cheese, it.supporters[i].cheese)
                    } else {
                        this.map { item -> item.visibility = View.GONE }
                    }
                }
            }
            if (it.supporters.isEmpty())
                findViewById<View>(R.id.line_chat_start).visibility = View.GONE
        }
    }
}