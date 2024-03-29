package com.github.tomasz_m.songapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.tomasz_m.songapp.R
import com.github.tomasz_m.songapp.databinding.ActivityMainBinding
import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongsUseCase
import com.github.tomasz_m.songapp.domain.Status
import com.github.tomasz_m.songapp.presentation.list.SongAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    inner class ViewModelFactory(private val songsUseCase: SongsUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SongsViewModel(songsUseCase) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val songsUseCase: SongsUseCase by inject()

        val model = ViewModelProviders.of(this, ViewModelFactory(songsUseCase)).get(SongsViewModel::class.java)

        val songAdapter = SongAdapter()

        binding.lifecycleOwner = this
        binding.viewModel = model
        binding.recyclerView.adapter = songAdapter

        val dataObserver = Observer<List<Song>> { data ->
            songAdapter.setData(data)
        }

        model.songs.observe(this, dataObserver)
        model.notifications.observe(this, Observer {
            it.getContentIfNotHandled()?.let { status ->
                displayStatusSnackbar(status)
            }
        })
    }

    private fun displayStatusSnackbar(status: Status) {
        val messageResId: Int? = useCaseStatusToMessageResId(status)
        if (messageResId != null) {
            Snackbar.make(findViewById(R.id.rootLayout), messageResId, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun useCaseStatusToMessageResId(status: Status): Int? {
        return when (status) {
            Status.OK -> null
            Status.NETWORK_ERROR -> R.string.message_check_internet_connection
            Status.NETWORK_ERROR_CASHED -> R.string.message_returned_cached
        }
    }
}
