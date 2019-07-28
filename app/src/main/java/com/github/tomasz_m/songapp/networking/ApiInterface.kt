package com.github.tomasz_m.songapp.networking

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("search?term=jack+johnson")
    fun getSongs(): Call<Model.Response>

}