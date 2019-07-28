package com.github.tomasz_m.songapp.domain

class SongsUseCaseImpl(val repository: SongRepository): SongsUseCase {

    override fun songs(): Array<Song> {
        return repository.getSongs(arrayOf(Source.LOCAL))
    }
}
