package com.example.androidtask

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidtask.data.Product
import com.example.androidtask.data.ProductManager
import com.example.androidtask.databinding.ActivityDetailBinding
import kotlin.random.Random

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val product by lazy { intent.getParcelableExtra(PRODUCT, Product::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initProduct()
        initBackButtonAction()
    }

    private fun initBackButtonAction() {
        binding.ivBack.setOnClickListener {
            val intent = Intent(this@DetailActivity, MainActivity::class.java)

            product?.let {
                val currentProduct = ProductManager.handleLike(it.id, binding.ivLike.isChecked)

                // Intent로 전달받은 상품과 현재 표시되고 있는 상품이 다르다면 (좋아요 상태가 바뀌었다면)
                if (it != currentProduct) {
                    intent.putExtra(PRODUCT, currentProduct)
                    setResult(RESULT_OK, intent)
                }
            }
            finish()
        }
    }

    private fun initProduct() {
        product?.let {
            val mannerTemperature = getRandomTemperature()
            val (image, color) = getResourceByTemperature(mannerTemperature)

            with(binding) {
                tvTemperature.apply {
                    text = getString(R.string.temperature, mannerTemperature)
                    setTextColor(getColor(color))
                }

                ivProduct.setImageResource(it.image)
                ivTemperature.setImageResource(image)
                tvSeller.text = it.seller
                tvAddress.text = it.address
                tvName.text = it.name
                tvDescription.text = it.description
                tvPrice.text = getString(R.string.price, it.price)
                ivLike.isChecked = it.isLiked
            }
        }
    }

    private fun getRandomTemperature(): Double {
        val random = Random.nextInt(1000) / 10.0
        return random
    }

    private fun getResourceByTemperature(temperature: Double) = when ((temperature * 10).toInt()) {
        in 0..125 -> R.drawable.img_manner_grade_0 to R.color.grade_0
        in 126..300 -> R.drawable.img_manner_grade_1 to R.color.grade_1
        in 301..365 -> R.drawable.img_manner_grade_2 to R.color.grade_2
        in 366..505 -> R.drawable.img_manner_grade_3 to R.color.grade_3
        in 506..655 -> R.drawable.img_manner_grade_4 to R.color.grade_4
        else -> R.drawable.img_manner_grade_5 to R.color.grade_5
    }
}