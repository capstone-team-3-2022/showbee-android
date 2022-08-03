package com.team3.showbee.di

import com.team3.showbee.SharedPref
import com.team3.showbee.data.network.NetworkResponseAdapterFactory
import com.team3.showbee.data.network.api.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val baseUrl = "http://117.17.102.143:8081/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor {
                val request = it.request()
                if (request.url.encodedPath.equals("/v1/signup", true)
                    || request.url.encodedPath.equals("/v1/signin", true)
                    || request.url.encodedPath.equals("/v1/check", true)
                ) {
                    it.proceed(request)
                } else {
                    it.proceed(request.newBuilder().apply {
                        addHeader("X-AUTH-TOKEN", SharedPref.getToken()!!.accessToken)
                    }.build())
                }
            }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): Service {
        return retrofit.create(Service::class.java)
    }
}