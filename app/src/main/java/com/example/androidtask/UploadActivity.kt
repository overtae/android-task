package com.example.androidtask

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UploadActivity : AppCompatActivity() {
    private val getTakePicturePreview =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap.let { findViewById<ImageView>(R.id.iv_img).setImageBitmap(bitmap) }
        }
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                uri.let { findViewById<ImageView>(R.id.iv_img).setImageURI(uri) }
            } else {
                Toast.makeText(this@UploadActivity, "선택된 사진이 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upload)
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

        // 카메라로 사진 찍기
        findViewById<TextView>(R.id.tv_camera).setOnClickListener {
            val cameraPermissionCheck = ContextCompat.checkSelfPermission(
                this@UploadActivity,
                Manifest.permission.CAMERA
            )
            if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    1000
                )
            } else getTakePicturePreview.launch(null)
        }

        // 갤러리에서 사진 가져오기
        findViewById<TextView>(R.id.tv_gallery).setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
}