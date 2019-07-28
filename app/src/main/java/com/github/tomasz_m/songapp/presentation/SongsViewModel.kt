package com.github.tomasz_m.songapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongsUseCase
import com.github.tomasz_m.songapp.domain.Source

class SongsViewModel(private val songsUseCase: SongsUseCase) : ViewModel() {

    private val _songs: MutableLiveData<List<Song>> by lazy {
        MutableLiveData<List<Song>>().also { liveData -> loadSongs(liveData) }
    }

    val songs: LiveData<List<Song>>
        get() = _songs

    private fun loadSongs(liveData: MutableLiveData<List<Song>>) {
        songsUseCase.songs(Source.ALL) {
            liveData.postValue(it)
        }
    }
}

