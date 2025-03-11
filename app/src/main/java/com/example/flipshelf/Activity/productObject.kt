package com.example.flipshelf.Activity

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object productObject {
    val retrofit: ProductInterface by lazy {
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductInterface::class.java)
    }
}