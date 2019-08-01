package com.github.tomasz_m.songapp.domain

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class SongsUseCaseImpl(private val localSongRepository: SongRepository, private val remoteSongRepository: SongRepository) :
    SongsUseCase {

    override suspend fun songs(source: Source): SongsUseCaseResponse {

        return when (source) {
            Source.LOCAL ->
                 SongsUseCaseResponse(localSongRepository.getSongs().songs, Status.OK)

            Source.REMOTE -> {
                val response = remoteSongRepository.getSongs()
                SongsUseCaseResponse(response.songs, remoteRepoStatusToUseCaseStatus(response.status))
            }
            Source.ALL -> {
                 coroutineScope {
                    val remote = async {remoteSongRepository.getSongs()}
                    val local = async {localSongRepository.getSongs()}
                    SongsUseCaseResponse(
                        remote.await().songs + local.await().songs,
                        remoteRepoStatusToUseCaseStatus(remote.await().status))
                }
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
