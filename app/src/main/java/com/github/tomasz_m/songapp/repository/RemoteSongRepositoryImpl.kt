package com.github.tomasz_m.songapp.repository

import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongRepository
import com.github.tomasz_m.songapp.networking.ApiInterface

class RemoteSongRepositoryImpl(private val api: ApiInterface) : SongRepository {
    override suspend fun getSongs(): List<Song> {

        val response = api.getSongs()

        if (response.body()?.results != null) {
            return response.body()!!.results.map { Song(it.trackName, it.artistName) };
        }
        return emptyList()


    }

}