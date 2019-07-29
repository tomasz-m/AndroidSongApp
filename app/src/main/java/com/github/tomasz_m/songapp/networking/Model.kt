package com.github.tomasz_m.songapp.networking

object Model {
    data class Response(val results: List<RemoteSong>)

    data class RemoteSong(val trackId: Int, val trackName: String, val artistName: String, val releaseDate: String)
}