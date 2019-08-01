package com.github.tomasz_m.songapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongsUseCase
import com.github.tomasz_m.songapp.domain.Source
import com.github.tomasz_m.songapp.domain.Status
import com.github.tomasz_m.songapp.presentation.helpers.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class SourceFilterOptions {
    REMOTE, LOCAL, ALL
}

class SongsViewModel(private val songsUseCase: SongsUseCase) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _showEmptyView = MutableLiveData<Boolean>().apply { value = false }
    val showEmptyView: LiveData<Boolean>
        get() = _showEmptyView
    private val _selectedRadioButton = MutableLiveData<SourceFilterOptions>().apply { value = SourceFilterOptions.ALL }
    val selectedRadioButton: LiveData<SourceFilterOptions>
        get() = _selectedRadioButton
    private val songsLazy = lazy {
        MutableLiveData<List<Song>>().also { liveData -> loadSongs(liveData) }
    }
    private val _songs: MutableLiveData<List<Song>> by songsLazy
    val songs: LiveData<List<Song>>
        get() = _songs
    private var currentJob: Job? = null


    private val _notifications = MutableLiveData<Event<Status>>()

    val notifications: LiveData<Event<Status>>
        get() = _notifications


    fun onSelectedRadioButton(filterOption: SourceFilterOptions) {
        _selectedRadioButton.value = filterOption
        onRefresh()
    }

    fun onRefresh() {
        if (songsLazy.isInitialized()) {
            loadSongs(_songs)
        }
    }


    private fun loadSongs(liveData: MutableLiveData<List<Song>>) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            _isLoading.postValue(true)
            val source = filterOptionToSource(selectedRadioButton.value)
            val response = songsUseCase.songs(source)
            _showEmptyView.postValue(response.songs.isEmpty())
            liveData.postValue(response.songs)
            _isLoading.postValue(false)
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

