package com.github.tomasz_m.songapp.domain

class SongsUseCaseImpl(private val localSongRepository: SongRepository, val remoteSongRepository: SongRepository) :
    SongsUseCase {

    override suspend fun songs(source: Source): SongsUseCaseResponse {

        when (source) {
            Source.LOCAL ->
                return SongsUseCaseResponse(localSongRepository.getSongs().songs, Status.OK)

            Source.REMOTE -> {
                val response = remoteSongRepository.getSongs()
                return SongsUseCaseResponse(response.songs, remoteRepoStatusToUseCaseStatus(response.status))
            }
            Source.ALL -> {
                val remote = remoteSongRepository.getSongs()
                val local = localSongRepository.getSongs()
                return SongsUseCaseResponse(
                    remote.songs + local.songs,
                    remoteRepoStatusToUseCaseStatus(remote.status)
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
