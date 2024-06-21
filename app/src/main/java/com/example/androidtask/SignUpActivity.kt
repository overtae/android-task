package com.example.androidtask

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
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
        val backButton = findViewById<Button>(R.id.btn_back)

        // 비밀번호 보이기/숨기기 토글
        togglePasswordButton.setOnCheckedChangeListener { _, isChecked ->
            passwordInput.transformationMethod =
                if (isChecked) HideReturnsTransformationMethod() else PasswordTransformationMethod()
            passwordInput.setSelection(passwordInput.text.length)
        }

        signUpButton.setOnClickListener {
            val name = nameInput.text.toString()
            val id = idInput.text.toString()
            val password = passwordInput.text.toString()

            // TODO: 비밀번호 유효성 검사
            if (name.isBlank() || id.isBlank() || password.isBlank()) {
                showToast("입력되지 않은 정보가 있습니다.")
            } else if (validateId(id)) {
                showToast("이미 사용중인 아이디입니다.")
            } else {
                val intent = Intent(this, SignInActivity::class.java)

                users.add(User(id, password, name))
                intent.putExtra("id", id)
                intent.putExtra("password", password)

                setResult(RESULT_OK, intent)
                finish()
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    // 외부 화면 터치 시 키보드 숨기기
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

    // 중복된 id 검사
    fun validateId(id: String): Boolean {
        return users.any {
            it.id == id
        }
    }

    // 비밀번호 검사
    fun validatePassword(password: String): Boolean {
        return true
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}