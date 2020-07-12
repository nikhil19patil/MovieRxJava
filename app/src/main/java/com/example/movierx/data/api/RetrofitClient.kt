package com.example.movierx.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val FIRST_PAGE = 1
const val BASE_URL = "http://www.omdbapi.com/"

object RetrofitClient {
    var okHttpClient = OkHttpClient.Builder()
        .build()

    var retrofit = Retrofit.Builder().baseUrl( BASE_URL )
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(this.okHttpClient)
        .build()

    fun getClient(): MovieDbInterface {
        return retrofit.create(MovieDbInterface::class.java)
    }
}