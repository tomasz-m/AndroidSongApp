package com.github.tomasz_m.songapp.repository

import android.content.Context
import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongRepository
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


data class FileSong(
    @SerializedName("Song Clean") val songName: String,
    @SerializedName("ARTIST CLEAN") val artistName: String
)

class LocalSongRepositoryImpl(private val appContext: Context) : SongRepository {
    override fun getSongs(callback: (List<Song>) -> Unit) {

        val inputStream = appContext.assets.open("localSongs.json")

        val songs = readJsonStream(inputStream)

        callback(songs.map{Song(it.songName, it.artistName)})
    }

    @Throws(IOException::class)
    fun readJsonStream(`in`: InputStream): List<FileSong> {
        val reader = JsonReader(InputStreamReader(`in`, "UTF-8"))
        val gson = Gson()
        val songs = ArrayList<FileSong>()
        reader.beginArray()
        while (reader.hasNext()) {
            val message = gson.fromJson<FileSong>(reader, FileSong::class.java)
            songs.add(message)
        }
        reader.endArray()
        reader.close()
        return songs
    }

}