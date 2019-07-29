package com.github.tomasz_m.songapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.tomasz_m.songapp.R
import com.github.tomasz_m.songapp.databinding.BindableAdapter
import com.github.tomasz_m.songapp.domain.Song
import kotlinx.android.synthetic.main.song_list_item.view.*

class SongAdapter : RecyclerView.Adapter<SongAdapter.SongsViewHolder>(), BindableAdapter<List<Song>> {
    override fun setData(data: List<Song>) {
        songs = data
        notifyDataSetChanged()
    }

    var songs = emptyList<Song>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SongsViewHolder(inflater.inflate(R.layout.song_list_item, parent, false))
    }

    override fun getItemCount() = songs.size

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    class SongsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(song: Song) {
            itemView.songName.text = song.name
            itemView.artistName.text = song.artist
            itemView.releaseYear.text = song.releaseYear
        }
    }
}
