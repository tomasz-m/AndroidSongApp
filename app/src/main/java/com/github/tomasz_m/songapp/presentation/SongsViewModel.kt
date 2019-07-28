package com.github.tomasz_m.songapp.presentation

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongsUseCase
import com.github.tomasz_m.songapp.domain.Source

enum class SourceFilterOptions {
    REMOTE, LOCAL, ALL
}

class SongsViewModel(private val songsUseCase: SongsUseCase) : ViewModel() {

    val isLoading = ObservableBoolean(false)
    val selectedRadioButton = ObservableField(SourceFilterOptions.ALL)

    fun onSelectedRadioButton(filterOption: SourceFilterOptions) {
        selectedRadioButton.set(filterOption)
        onRefresh()
    }

    fun onRefresh() {
        loadSongs(_songs)
    }

    private val _songs: MutableLiveData<List<Song>> by lazy {
        MutableLiveData<List<Song>>().also { liveData -> loadSongs(liveData) }
    }

    val songs: LiveData<List<Song>>
        get() = _songs

    private fun loadSongs(liveData: MutableLiveData<List<Song>>) {
        isLoading.set(true)
        val source = filterOptionToSource(selectedRadioButton.get())
        songsUseCase.songs(source) {
            liveData.postValue(it)
            isLoading.set(false)
        }
    }

    private fun filterOptionToSource(option: SourceFilterOptions?): Source {
        return when (option) {
            SourceFilterOptions.LOCAL -> Source.LOCAL
            SourceFilterOptions.REMOTE-> Source.REMOTE
            else -> Source.ALL
        }
    }
}

