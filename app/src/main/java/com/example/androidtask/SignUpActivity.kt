package com.example.androidtask

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameInput = findViewById<EditText>(R.id.et_name)
        val idInput = findViewById<EditText>(R.id.et_id)
        val passwordInput = findViewById<EditText>(R.id.et_password)

        val togglePasswordButton = findViewById<ToggleButton>(R.id.btn_toggle_password)
        val signUpButton = findViewById<Button>(R.id.btn_signup)

        // TODO: 비밀번호 유효성 검사
        // 비밀번호 보이기/숨기기 토글
        togglePasswordButton.setOnCheckedChangeListener { _, isChecked ->
            passwordInput.transformationMethod =
                if (isChecked) HideReturnsTransformationMethod() else PasswordTransformationMethod()
            passwordInput.setSelection(passwordInput.text.length)
        }

        signUpButton.setOnClickListener {
            // TODO: 빈 값 체크 외에도 기존 정보와 중복이 되는지도 체크하는 로직 추가
            if (nameInput.text.isEmpty() || idInput.text.isEmpty() || passwordInput.text.isEmpty()) {
                showToast("입력되지 않은 정보가 있습니다.")
            } else {
                showToast("회원 가입이 완료 되었습니다.")
                finish()
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}