package com.github.tomasz_m.songapp.repository

import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongRepository
import com.github.tomasz_m.songapp.domain.Source

class SongRepositoryImpl : SongRepository {
    override fun getSongs(sources: Array<Source>):Array<Song> {
        return  arrayOf(Song("Soap","Beans"), Song("Everybody","BSB"))
    }

}