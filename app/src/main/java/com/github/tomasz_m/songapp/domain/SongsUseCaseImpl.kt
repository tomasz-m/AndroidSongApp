package com.github.tomasz_m.songapp.domain

import kotlinx.coroutines.*
import com.github.tomasz_m.songapp.domain.SongsUseCase.*

class SongsUseCaseImpl(private val localSongRepository: SongRepository, val remoteSongRepository: SongRepository) :
    SongsUseCase {

    override fun songs(source: Source, callback: (List<Song>, Status) -> Unit) {

        when (source) {
            Source.LOCAL -> GlobalScope.launch {
                callback(localSongRepository.getSongs().songs, Status.OK)
            }
            Source.REMOTE -> GlobalScope.launch {
                val response = remoteSongRepository.getSongs()
                callback(response.songs, remoteRepoStatusToUseCaseStatus(response.status))
            }
            Source.ALL -> GlobalScope.launch {
                val remote = async { remoteSongRepository.getSongs() }
                val local = async { localSongRepository.getSongs() }
                callback(
                    remote.await().songs + local.await().songs,
                    remoteRepoStatusToUseCaseStatus(remote.await().status)
                )
            }
        }
    }

    private fun remoteRepoStatusToUseCaseStatus(repositoryStatus: SongRepository.Status): Status {
        return when (repositoryStatus) {
            SongRepository.Status.ERROR -> Status.NETWORK_ERROR
            SongRepository.Status.CACHED -> Status.NETWORK_ERROR_CASHED
            else -> Status.OK
        }
    }
}
