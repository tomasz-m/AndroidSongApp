package com.github.tomasz_m.songapp.networking

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("search?term=jack+johnson")
    suspend fun getSongs(): Response<Model.Response>
}