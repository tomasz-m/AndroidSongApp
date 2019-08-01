package com.github.tomasz_m.songapp.domain

import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


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
