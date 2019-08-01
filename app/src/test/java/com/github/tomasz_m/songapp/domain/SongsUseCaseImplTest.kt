package com.github.tomasz_m.songapp.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.nhaarman.mockitokotlin2.*


@RunWith(MockitoJUnitRunner::class)
class SongsUseCaseImplTest {

    @Mock
    private lateinit var localRepository: SongRepository

    @Mock
    private lateinit var remoteRepository: SongRepository


    @Test
    fun `requesting for remote data calls remote repository`() = runBlockingTest {
        val songsUseCase = SongsUseCaseImpl(localRepository, remoteRepository)
        whenever(remoteRepository.getSongs()).thenReturn(SongRepository.SongsResult(emptyList(),SongRepository.Status.OK))

        songsUseCase.songs(Source.REMOTE)

        verify(remoteRepository, times(1)).getSongs()
    }


    @Test
    fun `requesting for local data does not call remote repository`() = runBlockingTest {
        val songsUseCase = SongsUseCaseImpl(localRepository, remoteRepository)
        whenever(localRepository.getSongs()).thenReturn(SongRepository.SongsResult(emptyList(),SongRepository.Status.OK))

        songsUseCase.songs(Source.LOCAL)

        verify(remoteRepository, never()).getSongs()
    }
}
