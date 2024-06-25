package com.example.androidtask

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    private val headImages = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5
    )

    private val pikachuImages = listOf(
        R.drawable.pikachu1,
        R.drawable.pikachu2,
        R.drawable.pikachu3,
        R.drawable.pikachu4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val user = intent.getParcelableExtra("user", User::class.java)

        val idText = findViewById<TextView>(R.id.tv_id)
        val nameText = findViewById<TextView>(R.id.tv_name)
        val ageText = findViewById<TextView>(R.id.tv_age)
        val mbtiText = findViewById<TextView>(R.id.tv_mbti)
        val genderText = findViewById<TextView>(R.id.tv_gender)

        val image = findViewById<ImageView>(R.id.iv_image)
        val pikachuImage = findViewById<ImageView>(R.id.iv_pikachu)

        val exitButton = findViewById<ImageButton>(R.id.btn_exit)

        idText.text = getString(R.string.id_info, user?.id)
        nameText.text = getString(R.string.text_info, user?.name)
        ageText.text = getString(R.string.number_info, user?.age)
        mbtiText.text = getString(R.string.text_info, user?.mbti)
        genderText.text = getString(R.string.text_info, user?.gender)

        exitButton.setOnClickListener {
            finish()
        }

        val headImageRes = headImages.random()
        image.setImageResource(headImageRes)

        val pikachuImageRes = pikachuImages.random()
        pikachuImage.setImageResource(pikachuImageRes)
    }
}