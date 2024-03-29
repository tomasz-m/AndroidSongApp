package com.github.tomasz_m.songapp.di

import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongRepository
import com.github.tomasz_m.songapp.domain.SongsUseCase
import com.github.tomasz_m.songapp.domain.SongsUseCaseImpl
import com.github.tomasz_m.songapp.repository.networking.ApiClient
import com.github.tomasz_m.songapp.repository.Cache
import com.github.tomasz_m.songapp.repository.InMemoryCache
import com.github.tomasz_m.songapp.repository.LocalSongRepositoryImpl
import com.github.tomasz_m.songapp.repository.RemoteSongRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single { ApiClient.getClient }
    single<SongRepository>(named("localRepository")) { LocalSongRepositoryImpl(androidContext()) }
    single<Cache<List<Song>>> { InMemoryCache() }
    single<SongRepository>(named("remoteRepository")) { RemoteSongRepositoryImpl(get(), get()) }
    single<SongsUseCase> {
        SongsUseCaseImpl(get(named("localRepository")), get(named("remoteRepository")))
    }
}