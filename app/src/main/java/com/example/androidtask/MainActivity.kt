package com.example.androidtask

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.adapter.ProductAdapter
import com.example.androidtask.data.Product
import com.example.androidtask.data.ProductManager
import com.example.androidtask.databinding.ActivityMainBinding

const val PRODUCT = "product"

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val productAdapter by lazy { ProductAdapter(this).apply { submitList(ProductManager.getProducts()) } }
    private lateinit var getLikedResult: ActivityResultLauncher<Intent>

    // 알림
    private val notificationID = 1
    private val channelId = "apple_market_keyword"
    private val channelName = "키워드 알림"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initProducts()
        initScrollToTopButton()

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                alertDialog("종료", "정말 종료하시겠습니까?", "종료") { finish() }
            }
        })

        binding.ivNotification.setOnClickListener {
            askPermission()
            sendNotification()
        }
    }

    private fun initProducts() {
        getLikedResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    productAdapter.submitList(ProductManager.getProducts())
                }
            }

        with(binding.rvMain) {
            adapter = productAdapter.apply {
                itemClickListener = object : ProductAdapter.ItemClickListener {
                    override fun onClick(item: Product) {
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra(PRODUCT, item)
                        getLikedResult.launch(intent)
                    }

                    override fun onLongClick(item: Product) {
                        alertDialog("삭제", "정말 삭제하시겠습니까?", "삭제") {
                            ProductManager.deleteProduct(item)
                            productAdapter.submitList(ProductManager.getProducts().toList())
                        }
                    }
                }
            }
            addItemDecoration(
                DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            )
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun initScrollToTopButton() {
        with(binding.rvMain) {
            setOnClickListener { smoothScrollToPosition(0) }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                var isVisible = false
                val fadeOut = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_out)
                val fadeIn = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in)

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    binding.ivToTop.apply {
                        if (!recyclerView.canScrollVertically(-1)) {
                            startAnimation(fadeOut)
                            isVisible = false
                        }

                        if (!isVisible && dy > 0) {
                            startAnimation(fadeIn)
                            isVisible = true
                        }
                    }
                }
            })
        }
    }

    private fun alertDialog(
        title: String,
        message: String,
        positiveButtonText: String,
        action: () -> Unit
    ) {
        val listener = DialogInterface.OnClickListener { _, p1 ->
            when (p1) {
                DialogInterface.BUTTON_POSITIVE -> action()
            }
        }

        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setIcon(R.drawable.ic_comment)
        }.apply {
            setPositiveButton(positiveButtonText, listener)
            setNegativeButton("취소", listener)
        }.show()
    }

    private fun sendNotification() {
        val builder = NotificationCompat.Builder(this, channelId)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = getNotificationChannel()
        manager.createNotificationChannel(channel)

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        builder.run {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())
            setContentTitle("키워드 알림")
            setContentText("설정한 키워드에 대한 알림이 도착했습니다!!")
            setContentIntent(pendingIntent)
        }

        manager.notify(notificationID, builder.build())
    }

    // 알림을 만들 때 마다 매번 실행되도 상관 X
    // -> 이미 존재하는 채널을 다시 create 한다 해도 아무 일도 일어나지 않기 때문이다.
    private fun getNotificationChannel(): NotificationChannel = NotificationChannel(
        channelId, channelName,
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
        description = "사과 마켓의 키워드 알림 서비스"
        setShowBadge(true)
        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()
        setSound(uri, audioAttributes)
        enableVibration(true)
    }

    private fun askPermission() {
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            }
            startActivity(intent)
        }
    }
}