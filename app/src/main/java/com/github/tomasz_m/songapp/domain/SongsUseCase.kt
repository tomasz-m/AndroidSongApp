package com.github.tomasz_m.songapp.domain

interface SongsUseCase {
    fun songs(source: Source, callback: (List<Song>) -> Unit)
}