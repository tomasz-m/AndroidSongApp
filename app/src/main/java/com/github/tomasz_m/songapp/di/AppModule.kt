package com.github.tomasz_m.songapp.di

import com.github.tomasz_m.songapp.domain.SongRepository
import com.github.tomasz_m.songapp.domain.SongsUseCase
import com.github.tomasz_m.songapp.domain.SongsUseCaseImpl
import com.github.tomasz_m.songapp.networking.ApiClient
import com.github.tomasz_m.songapp.repository.LocalSongRepositoryImpl
import com.github.tomasz_m.songapp.repository.RemoteSongRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single { ApiClient.getClient }
    single<SongRepository>(named("local")) { LocalSongRepositoryImpl(androidContext()) }
    single<SongRepository>(named("remote")) { RemoteSongRepositoryImpl(get()) }
    single<SongsUseCase> { SongsUseCaseImpl(get(named("local")), get(named("remote"))) }

}