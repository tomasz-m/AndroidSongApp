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
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SongsUseCaseImplTest {

    @Mock
    private lateinit var localRepository: SongRepository

    @Mock
    private lateinit var remoteRepository: SongRepository

    @Mock
    private lateinit var callback: (List<Song>) -> Unit


    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    @Test
    fun `requesting for remote data does not call local repository`() = runBlockingTest {

        val songsUseCase = SongsUseCaseImpl(localRepository, remoteRepository)

        songsUseCase.songs(Source.REMOTE, callback)

        verify(localRepository, never()).getSongs()

    }
}