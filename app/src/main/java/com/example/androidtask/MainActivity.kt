package com.example.androidtask

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask.adapter.ProductAdapter
import com.example.androidtask.data.Product
import com.example.androidtask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val items = listOf(
            Product(
                1,
                R.drawable.img_sample1,
                "상품 이름은 최대 두 줄이고, 그래도 넘어가면 뒷 부분에 ...으로 처리해주세요.상품 이름은 최대 두 줄이고, 그래도 넘어가면 뒷 부분에 ...으로 처리해주세요.",
                "설명입니다",
                "판매자",
                2012704981,
                "수원시 영통구 원천동",
                20,
                20
            ),
            Product(
                1,
                R.drawable.img_sample1,
                "상품 이름은 최대 두 줄이고, 그래도 넘어가면 뒷 부분에 ...으로 처리해주세요.상품 이름은 최대 두 줄이고, 그래도 넘어가면 뒷 부분에 ...으로 처리해주세요.",
                "설명입니다",
                "판매자",
                2012704981,
                "수원시 영통구 원천동",
                20,
                20
            ),
            Product(
                1,
                R.drawable.img_sample1,
                "상품 이름은 최대 두 줄이고, 그래도 넘어가면 뒷 부분에 ...으로 처리해주세요.상품 이름은 최대 두 줄이고, 그래도 넘어가면 뒷 부분에 ...으로 처리해주세요.",
                "설명입니다",
                "판매자",
                2012704981,
                "수원시 영통구 원천동",
                20,
                20
            ),
        )

        val adapter = ProductAdapter(items)
        binding.rvMain.adapter = adapter
        binding.rvMain.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvMain.layoutManager = LinearLayoutManager(this)
    }
}