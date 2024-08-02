package com.example.androidtask.network

import com.example.androidtask.BuildConfig
import com.example.androidtask.data.remote.SearchImage
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.Throws

object NetworkClient {
    private const val BASE_URL = "https://dapi.kakao.com/v2/search/"
    val kakaoNetwork: SearchImage by lazy { getClient().create(SearchImage::class.java) }

    private fun getClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(NetworkInterceptor()))
            .build()
    }

    private fun getOkHttpClient(interceptor: NetworkInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .run {
                addInterceptor(interceptor)
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                build()
            }
    }

    class NetworkInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_API_KEY}")
                .build()
            return chain.proceed(newRequest)
        }
    }
}