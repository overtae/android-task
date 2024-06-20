package com.example.androidtask

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    val colors = mapOf(
        "blue" to R.drawable.blue_flower,
        "pink" to R.drawable.pink_flower,
        "purple" to R.drawable.purple_flower,
        "red" to R.drawable.red_flower,
        "yellow" to R.drawable.yellow_flower
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

        val id = intent.getStringExtra("id") ?: ""
        val user = findUserById(id)

        val idText = findViewById<TextView>(R.id.tv_id)
        val nameText = findViewById<TextView>(R.id.tv_name)

        val image = findViewById<ImageView>(R.id.iv_image)
        val exitButton = findViewById<Button>(R.id.btn_exit)

        idText.text = getString(R.string.id_info, user?.id)
        nameText.text = getString(R.string.name_info, user?.name)

        exitButton.setOnClickListener {
            finish()
        }

        val color = colors.keys.random()
        image.setImageResource(colors[color]!!)
    }

    private fun findUserById(id: String): User? {
        return users.find { it.id == id }
    }
}