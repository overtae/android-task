package com.example.androidtask

data class User(
    val id: String,
    val password: String,
    val name: String,
    val age: Int = 20,
    val mbti: String = "ISFP",
    val gender: String = "여자"
)