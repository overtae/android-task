package com.example.androidtask

import android.content.Intent
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

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idInput = findViewById<EditText>(R.id.et_id)
        val passwordInput = findViewById<EditText>(R.id.et_password)

        val togglePasswordButton = findViewById<ToggleButton>(R.id.btn_toggle_password)
        val signInButton = findViewById<Button>(R.id.btn_signin)
        val signUpButton = findViewById<Button>(R.id.btn_signup)

        // 회원 가입 결과 가져오기
        val getSignUpResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val id = it.data?.getStringExtra("id") ?: ""
                    val password = it.data?.getStringExtra("password") ?: ""

                    idInput.setText(id)
                    passwordInput.setText(password)

                    showToast("회원 가입 성공")
                }
            }

        // 비밀번호 보이기/숨기기 토글
        togglePasswordButton.setOnCheckedChangeListener { _, isChecked ->
            passwordInput.transformationMethod =
                if (isChecked) HideReturnsTransformationMethod() else PasswordTransformationMethod()
            passwordInput.setSelection(passwordInput.text.length)
        }

        // 로그인 버튼
        signInButton.setOnClickListener {
            if (idInput.text.isEmpty() || passwordInput.text.isEmpty()) {
                showToast("아이디/비밀번호를 확인해주세요.")
            } else {
                val intent = Intent(this, HomeActivity::class.java)

                // TODO: 만약 등록이 되어 있는 아이디/비밀번호라면,
                println(idInput.text)
                intent.putExtra("id", idInput.text.toString())
                showToast("로그인 성공")
                startActivity(intent)
            }
        }

        // 회원가입 버튼
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            getSignUpResult.launch(intent)
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

