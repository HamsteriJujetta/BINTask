package com.example.bintask.network.di

import android.util.Log
import com.example.bintask.base.BASE_URL
import com.example.bintask.network.data.BINApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)
        }.apply {
            setLevel(HttpLoggingInterceptor.Level.HEADERS)
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    single<OkHttpClient> {
        OkHttpClient
            .Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single<Retrofit> {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get<OkHttpClient>())
            .build()
    }

    single<BINApi> {
        get<Retrofit>().create(BINApi::class.java)
    }

}