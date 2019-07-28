package com.github.tomasz_m.songapp.domain

class SongsUseCaseImpl(private val localSongRepository: SongRepository, val remoteSongRepository: SongRepository) : SongsUseCase {

    override fun songs(source: Source, callback: (List<Song>) -> Unit) {

        if(source == Source.LOCAL) {
            localSongRepository.getSongs { newSongs: List<Song> -> callback(newSongs) }
        }else if(source == Source.REMOTE) {
            remoteSongRepository.getSongs { newSongs: List<Song> -> callback(newSongs) }
        }
    }
}
