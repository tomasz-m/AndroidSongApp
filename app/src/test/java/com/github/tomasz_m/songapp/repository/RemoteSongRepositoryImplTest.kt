package com.github.tomasz_m.songapp.repository

import com.github.tomasz_m.songapp.domain.Song
import com.github.tomasz_m.songapp.networking.ApiInterface
import com.github.tomasz_m.songapp.networking.Model
import org.junit.Test
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RemoteSongRepositoryImplTest {

    @Mock
    lateinit var api: ApiInterface

    @Test
    fun `returns list of songs`() = runBlockingTest {
        val remoteSongRepositoryImpl = RemoteSongRepositoryImpl(api)

        val response: Response<Model.Response> =
            Response.success(Model.Response(listOf(Model.RemoteSong(1, "aaa", "bbb"))))

        whenever(api.getSongs()).thenReturn(response)


        assertEquals(
            remoteSongRepositoryImpl.getSongs(),
            listOf(Song("aaa", "bbb"))
        )

    }
}