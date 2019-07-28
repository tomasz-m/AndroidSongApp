package com.github.tomasz_m.songapp.domain

enum class Source {
    REMOTE, LOCAL, ALL
}

interface SongRepository {
    fun getSongs(callback: (List<Song>) -> Unit )
}