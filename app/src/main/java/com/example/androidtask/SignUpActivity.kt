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

            val idMessage = validateId(id)
            val passwordMessage = validatePassword(password)

            if (name.isBlank() || id.isBlank() || password.isBlank()) {
                showToast("입력되지 않은 정보가 있습니다.")
            } else if (idMessage.isNotEmpty()) {
                showToast(idMessage)
            } else if (passwordMessage.isNotEmpty()) {
                showToast(passwordMessage)
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

    /**
     * 아이디 유효성 검사
     * - 6 ~ 12자 사이
     * - 영문, 숫자만 가능
     * - 다른 유저와 중복 불가
     * */
    fun validateId(id: String): String {
        val isIdExist = users.any { it.id == id }
        val isAvailableLength = id.length in 6..12
        val isAvailableCharacter = id.matches(Regex("^[a-z]+[a-z0-9]*"))

        return when {
            isIdExist -> "이미 사용 중인 아이디입니다."
            !isAvailableLength -> "아이디는 6 ~ 12자 사이여야 합니다."
            !isAvailableCharacter -> "아이디는 영문, 숫자만 가능합니다."
            else -> ""
        }
    }

    /** 비밀번호 유효성 검사
     * - 8 ~ 20자 사이
     * - 영문, 숫자, 특수 문자 모두 사용
     * - 사용가능 특수 문자: ~!@#$%&*-_
     * - 첫 문자는 영문 소문자 또는 대문자만 가능
     */
    fun validatePassword(password: String): String {
        val isAvailableLength = password.length in 8..20
        val isAvailableCharacter =
            password.matches(Regex("^[a-zA-Z](?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#\$%&*-_]).*\$"))

        return when {
            !isAvailableLength -> "비밀번호는 8 ~ 20자 사이여야 합니다."
            !isAvailableCharacter -> "비밀번호는 영문, 숫자, 특수 문자 모두 사용해야 합니다."
            else -> ""
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}