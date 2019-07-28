package com.github.tomasz_m.songapp.di

import com.github.tomasz_m.songapp.domain.SongRepository
import com.github.tomasz_m.songapp.domain.SongsUseCase
import com.github.tomasz_m.songapp.domain.SongsUseCaseImpl
import com.github.tomasz_m.songapp.repository.SongRepositoryImpl
import org.koin.dsl.module

val appModule = module {

    single<SongRepository> { SongRepositoryImpl() }
    single<SongsUseCase> { SongsUseCaseImpl(get()) }

}