package com.team3.showbee.di

import com.team3.showbee.data.network.Service
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRequestFactory {
    private const val baseUrl = "http://117.17.102.143:8081/"

    val retrofit: Service = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }).build())
        .build()
        .create(Service::class.java)
}