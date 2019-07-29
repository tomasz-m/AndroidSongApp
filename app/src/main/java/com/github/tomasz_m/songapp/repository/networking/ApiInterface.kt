package com.github.tomasz_m.songapp.repository.networking

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    /**
     * it searches for jack johnson ony - it's a TODO
     */
    @GET("search?term=jack+johnson")
    suspend fun getSongs(): Response<Model.Response>
}