package com.github.tomasz_m.songapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.tomasz_m.songapp.presentation.helpers.BindableAdapter
import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.databinding.SongListItemBinding


class SongAdapter : RecyclerView.Adapter<SongAdapter.SongsViewHolder>(),
    BindableAdapter<List<Song>> {

    override fun setData(data: List<Song>) {
        songs = data
        notifyDataSetChanged()
    }

    var songs = emptyList<Song>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = SongListItemBinding.inflate(layoutInflater, parent, false)
        return SongsViewHolder(itemBinding)

    }

    override fun getItemCount() = songs.size

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    inner class SongsViewHolder(private val binding: SongListItemBinding) : RecyclerView.ViewHolder(binding.getRoot()) {

        fun bind(item: Song) {
            binding.song = item
            binding.executePendingBindings()
        }
    }
}
