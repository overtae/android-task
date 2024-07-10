package com.example.androidtask.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int, // 번호
    val image: Int, // 이미지 파일명
    val name: String, // 상품명
    val description: String, // 상품 소개
    val seller: String, // 판매자
    val price: Int, // 가격
    val address: String, // 주소
    val likes: Int, // 좋아요
    val chat: Int, // 채팅
) : Parcelable