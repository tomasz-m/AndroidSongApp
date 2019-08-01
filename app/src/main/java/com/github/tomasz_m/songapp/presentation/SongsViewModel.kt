package com.github.tomasz_m.songapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongsUseCase
import com.github.tomasz_m.songapp.domain.Source
import com.github.tomasz_m.songapp.domain.Status
import com.github.tomasz_m.songapp.presentation.helpers.Event
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

enum class SourceFilterOptions {
    REMOTE, LOCAL, ALL
}

class SongsViewModel(private val songsUseCase: SongsUseCase) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val showEmptyView = MutableLiveData<Boolean>().apply { value = false }
    val selectedRadioButton = MutableLiveData<SourceFilterOptions>().apply { value = SourceFilterOptions.ALL }


    private val _songs: MutableLiveData<List<Song>> by lazy {
        MutableLiveData<List<Song>>().also { liveData -> loadSongs(liveData) }
    }

    private val _notifications = MutableLiveData<Event<Status>>()

    val notifications: LiveData<Event<Status>>
        get() = _notifications


    val songs: LiveData<List<Song>>
        get() = _songs

    fun onSelectedRadioButton(filterOption: SourceFilterOptions) {
        selectedRadioButton.value = filterOption
        onRefresh()
    }

    fun onRefresh() {
        _songs.apply { loadSongs(this) }
//        loadSongs(_songs)
    }


    private fun loadSongs(liveData: MutableLiveData<List<Song>>) {
        isLoading.value = true
        GlobalScope.launch {
            val source = filterOptionToSource(selectedRadioButton.value)
            val response = songsUseCase.songs(source)
            showEmptyView.postValue(response.songs.isEmpty())
            liveData.postValue(response.songs)
            isLoading.postValue(false)
            _notifications.postValue(Event(response.status))

        }
    }

    private fun filterOptionToSource(option: SourceFilterOptions?): Source {
        return when (option) {
            SourceFilterOptions.LOCAL -> Source.LOCAL
            SourceFilterOptions.REMOTE -> Source.REMOTE
            else -> Source.ALL
        }
    }
}

