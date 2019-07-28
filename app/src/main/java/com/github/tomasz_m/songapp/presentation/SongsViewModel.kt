package com.github.tomasz_m.songapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongsUseCase

class SongsViewModel(private val songsUseCase: SongsUseCase) : ViewModel() {

    private val _songs: MutableLiveData<Array<Song>> by lazy {
        MutableLiveData<Array<Song>>().also { liveData -> loadSongs(liveData) }
    }

    val songs: LiveData<Array<Song>>
        get() = _songs

    private fun loadSongs(liveData: MutableLiveData<Array<Song>>) {
        liveData.value = songsUseCase.songs()
    }
}

