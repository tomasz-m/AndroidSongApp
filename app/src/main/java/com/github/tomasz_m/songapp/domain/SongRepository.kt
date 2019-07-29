package com.github.tomasz_m.songapp.domain

interface SongRepository {
    data class SongsResult(val songs: List<Song>, val status: Status)

    enum class Status {
        OK, ERROR, CACHED
    }

    suspend fun getSongs(): SongsResult
}