package com.github.tomasz_m.songapp.domain

interface SongsUseCase {
    enum class Source {
        REMOTE, LOCAL, ALL
    }
    enum class Status {
        NETWORK_ERROR, NETWORK_ERROR_CASHED, OK
    }
    fun songs(source: Source, callback: (List<Song>, Status) -> Unit)
}