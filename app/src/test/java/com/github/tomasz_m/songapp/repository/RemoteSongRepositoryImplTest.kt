package com.github.tomasz_m.songapp.repository

import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.domain.SongRepository
import com.github.tomasz_m.songapp.networking.ApiInterface
import com.github.tomasz_m.songapp.networking.Model
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RemoteSongRepositoryImplTest {

    @Mock
    lateinit var api: ApiInterface

    @ExperimentalCoroutinesApi
    @Test
    fun `returns list of songs`() = runBlockingTest {
        val remoteSongRepositoryImpl = RemoteSongRepositoryImpl(api)

        val response: Response<Model.Response> =
            Response.success(Model.Response(listOf(Model.RemoteSong(1, "aaa", "bbb", "2019"))))

        whenever(api.getSongs()).thenReturn(response)


        assertEquals(
            remoteSongRepositoryImpl.getSongs().songs,
            listOf(Song("aaa", "bbb", "2019"))
        )

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `returns error status when request fails`() = runBlockingTest {
        val remoteSongRepositoryImpl = RemoteSongRepositoryImpl(api)

        whenever(api.getSongs()).thenReturn(null)

        assertEquals(
            remoteSongRepositoryImpl.getSongs().status,
            SongRepository.Status.ERROR
        )

    }
}