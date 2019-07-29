package com.github.tomasz_m.songapp.presentation

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongsUseCase
import com.github.tomasz_m.songapp.presentation.helpers.Event

enum class SourceFilterOptions {
    REMOTE, LOCAL, ALL
}

class SongsViewModel(private val songsUseCase: SongsUseCase) : ViewModel() {
    val isLoading = ObservableBoolean(false)
    val showEmptyView = ObservableBoolean(false)
    val selectedRadioButton = ObservableField(SourceFilterOptions.ALL)

    private val _songs: MutableLiveData<List<Song>> by lazy {
        MutableLiveData<List<Song>>().also { liveData -> loadSongs(liveData) }
    }
    private val _notifications = MutableLiveData<Event<SongsUseCase.Status>>()

    val notifications: LiveData<Event<SongsUseCase.Status>>
        get() = _notifications


    val songs: LiveData<List<Song>>
        get() = _songs

    fun onSelectedRadioButton(filterOption: SourceFilterOptions) {
        selectedRadioButton.set(filterOption)
        onRefresh()
    }

    fun onRefresh() {
        loadSongs(_songs)
    }


    private fun loadSongs(liveData: MutableLiveData<List<Song>>) {
        isLoading.set(true)
        val source = filterOptionToSource(selectedRadioButton.get())
        songsUseCase.songs(source) { newSongs, status ->
            showEmptyView.set(newSongs.isEmpty())
            liveData.postValue(newSongs)
            isLoading.set(false)
            _notifications.postValue(Event(status))
        }
    }

    private fun filterOptionToSource(option: SourceFilterOptions?): SongsUseCase.Source {
        return when (option) {
            SourceFilterOptions.LOCAL -> SongsUseCase.Source.LOCAL
            SourceFilterOptions.REMOTE -> SongsUseCase.Source.REMOTE
            else -> SongsUseCase.Source.ALL
        }
    }
}

