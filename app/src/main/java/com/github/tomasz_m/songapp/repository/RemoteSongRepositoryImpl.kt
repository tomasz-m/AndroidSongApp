package com.github.tomasz_m.songapp.repository

import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongRepository
import com.github.tomasz_m.songapp.domain.SongRepository.*
import com.github.tomasz_m.songapp.networking.ApiInterface
import org.koin.ext.isInt
import java.lang.Exception

class RemoteSongRepositoryImpl(private val api: ApiInterface) : SongRepository {
    private fun stringDateToYear(date: String): String {
        val year = date.split("-")[0]
        if (!year.isInt()) {
            return ""
        }
        return year
    }

    override suspend fun getSongs(): SongsResult {
        try {
            val response = api.getSongs()
            if (!response.isSuccessful) {
                return SongsResult(emptyList(), Status.ERROR)
            }

            if (response.body()?.results == null) {
                return SongsResult(emptyList(), Status.ERROR)
            }

            val songs = response.body()!!.results
                .map { Song(it.trackName, it.artistName, stringDateToYear(it.releaseDate)) }
            return SongsResult(songs, Status.OK)

        } catch (e: Exception) {
            return SongsResult(emptyList(), Status.ERROR)
        }
    }
}