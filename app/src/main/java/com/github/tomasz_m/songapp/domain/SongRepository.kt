package com.github.tomasz_m.songapp.domain

enum class Source {
    REMOTE, LOCAL
}

interface SongRepository {
    fun getSongs(sources: Array<Source>):Array<Song>
}