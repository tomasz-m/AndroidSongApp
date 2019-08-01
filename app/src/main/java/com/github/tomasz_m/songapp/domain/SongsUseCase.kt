package com.github.tomasz_m.songapp.domain

enum class Source {
    REMOTE, LOCAL, ALL
}

enum class Status {
    NETWORK_ERROR, NETWORK_ERROR_CASHED, OK
}

data class SongsUseCaseResponse(val songs:List<Song>, val status: Status)

interface SongsUseCase {


    suspend fun songs(source: Source):SongsUseCaseResponse
}