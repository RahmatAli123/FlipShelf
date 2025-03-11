package com.example.flipshelf.Activity

import com.example.flipshelf.Model.productModel
import retrofit2.Call
import retrofit2.http.GET

interface ProductInterface {
    @GET("products")
    fun getProduct(): Call<List<productModel>>
}