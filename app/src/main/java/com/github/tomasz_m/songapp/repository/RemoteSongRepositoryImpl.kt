package com.github.tomasz_m.songapp.repository

import android.util.Log
import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongRepository
import com.github.tomasz_m.songapp.networking.ApiClient
import com.github.tomasz_m.songapp.networking.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteSongRepositoryImpl : SongRepository {
    override fun getSongs(callback: (List<Song>) -> Unit) {

        ApiClient.getClient.getSongs().enqueue(object : Callback<Model.Response> {

            override fun onResponse(call: Call<Model.Response>?, response: Response<Model.Response>?) {
                if (response?.body()?.results != null) {
                    val mapped = response.body()!!.results.map { Song(it.trackName, it.artistName) }
                    callback(mapped)
                }
            }

            override fun onFailure(call: Call<Model.Response>?, t: Throwable?) {
                Log.d("onFailure", "onFailure")
            }

        })

    }

}