package com.github.tomasz_m.songapp.repository

import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongRepository
import com.github.tomasz_m.songapp.domain.SongRepository.*
import com.github.tomasz_m.songapp.repository.networking.ApiInterface
import org.koin.ext.isInt
import java.lang.Exception

class RemoteSongRepositoryImpl(private val api: ApiInterface, private val cache: Cache<List<Song>>) : SongRepository {
    private fun stringDateToYear(date: String): String {
        val year = date.split("-")[0]
        if (!year.isInt()) {
            return ""
        }
        return year
    }

    override suspend fun getSongs(): SongsResult {
        if (cache.hasFreshCash("all-songs")) {
            cache.getLatestCash("all-songs")?.apply { return (SongsResult(this, Status.OK)) }
        }

        return try {
            val response = api.getSongs()
            if (!response.isSuccessful || response.body()?.results == null) {
                getErrorResponse()
            }else {
                val songs = response.body()!!.results
                    .map { Song(it.trackName, it.artistName, stringDateToYear(it.releaseDate)) }
                cache.setCash("all-songs", songs)
                SongsResult(songs, Status.OK)
            }

        } catch (e: Exception) {
            getErrorResponse()
        }
    }

    private fun getErrorResponse(): SongsResult {
        cache.getLatestCash("all-songs")?.apply { return (SongsResult(this, Status.CACHED)) }
        return SongsResult(emptyList(), Status.ERROR)
    }
}