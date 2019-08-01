package com.github.tomasz_m.songapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.tomasz_m.songapp.domain.SongsUseCase
import com.github.tomasz_m.songapp.domain.SongsUseCaseResponse
import com.github.tomasz_m.songapp.domain.Source
import com.github.tomasz_m.songapp.domain.Status
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SongsViewModelTest {

    @Mock
    lateinit var songsUseCase: SongsUseCase

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Test
    fun `selecting of remote source sets requests for data from remote repository`()  = runBlockingTest {
        val viewModel = SongsViewModel(songsUseCase)
        whenever(songsUseCase.songs(any())).thenReturn(SongsUseCaseResponse(emptyList(), Status.OK))

        viewModel.onSelectedRadioButton(SourceFilterOptions.REMOTE)

        verify(songsUseCase, times(1)).songs(eq(Source.REMOTE))
    }

}