package com.oba.monietracker.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Api {
    private const val BASE_URL = "https://api.unsplash.com/"

    // convert JSON response into objects that the project models can understand
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // initialization of retrofit instance
    private val retrofit = Retrofit
        .Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val photoRetrofitService: PhotoService by lazy {
        // instantiate our retrofit service i.e. the PhotoService
        retrofit.create(PhotoService::class.java)
    }
}