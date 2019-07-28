package com.github.tomasz_m.songapp.domain

import kotlinx.coroutines.*

class SongsUseCaseImpl(private val localSongRepository: SongRepository, val remoteSongRepository: SongRepository) :
    SongsUseCase {

    override fun songs(source: Source, callback: (List<Song>) -> Unit) {

        if (source == Source.LOCAL) {
            GlobalScope.launch { callback(localSongRepository.getSongs()) }
        } else if (source == Source.REMOTE) {
            GlobalScope.launch { callback(remoteSongRepository.getSongs()) }
        } else if (source == Source.ALL) {
            GlobalScope.launch {
                val remote = remoteSongRepository.getSongs()
                val local =localSongRepository.getSongs()
                callback(remote + local)
            }
        }
    }
}
