package com.github.tomasz_m.songapp.presentation

import android.os.Bundle
import android.util.Log
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
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    inner class ViewModelFactory(private val songsUseCase: SongsUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SongsViewModel(songsUseCase) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val songsUseCase: SongsUseCase by inject()

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val model = ViewModelProviders.of(this, ViewModelFactory(songsUseCase)).get(SongsViewModel::class.java)

        val songAdapter = SongAdapter()

        binding.lifecycleOwner = this
        binding.viewModel = model
        binding.recyclerView.adapter = songAdapter

        val dataObserver = Observer<List<Song>> { data ->
            songAdapter.setData(data)
            Log.d("newData", "size" + data.size)
        }

        model.songs.observe(this, dataObserver)
    }
}
