package com.example.androidtask.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidtask.R

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        overridePendingTransition(R.anim.bottom_to_top, R.anim.none)

        findViewById<ImageView>(R.id.iv_close).setOnClickListener {
            finish()
            overridePendingTransition(R.anim.none, R.anim.top_to_bottom)
        }
        
        findViewById<ImageView>(R.id.iv_search).setOnClickListener {
            val intent = Intent(this@SearchActivity, MainActivity::class.java).apply {
                putExtra(SEARCH_RESULT, findViewById<EditText>(R.id.et_search).text.toString())
            }
            setResult(RESULT_OK, intent)
            finish()
            overridePendingTransition(R.anim.none, R.anim.top_to_bottom)
        }
    }
}