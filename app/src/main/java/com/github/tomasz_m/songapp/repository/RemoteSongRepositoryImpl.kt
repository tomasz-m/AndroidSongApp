package com.github.tomasz_m.songapp.repository

import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongRepository
import com.github.tomasz_m.songapp.domain.SongRepository.*
import com.github.tomasz_m.songapp.repository.networking.ApiInterface
import org.koin.ext.isInt
import java.lang.Exception

class RemoteSongRepositoryImpl(private val api: ApiInterface, private val cash: Cash<List<Song>>) : SongRepository {
    private fun stringDateToYear(date: String): String {
        val year = date.split("-")[0]
        if (!year.isInt()) {
            return ""
        }
        return year
    }

    override suspend fun getSongs(): SongsResult {
        if (cash.hasFreshCash("all-songs")) {
            val cashedSongs = cash.getLatestCash("all-songs")
            if (cashedSongs != null) {
                return SongsResult(cashedSongs, Status.OK)
            }
        }

        try {
            val response = api.getSongs()
            if (!response.isSuccessful) {
                return getErrorResponse()
            }

            if (response.body()?.results == null) {
                return getErrorResponse()
            }

            val songs = response.body()!!.results
                .map { Song(it.trackName, it.artistName, stringDateToYear(it.releaseDate)) }
            cash.setCash("all-songs", songs)
            return SongsResult(songs, Status.OK)

        } catch (e: Exception) {
            return getErrorResponse()
        }
    }

    private fun getErrorResponse(): SongsResult {
        val cashedSongs = cash.getLatestCash("all-songs")
        if (cashedSongs != null) {
            return SongsResult(cashedSongs, Status.CACHED)
        }
        return SongsResult(emptyList(), Status.ERROR)
    }
}