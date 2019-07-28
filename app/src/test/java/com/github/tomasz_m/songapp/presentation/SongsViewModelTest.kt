package com.github.tomasz_m.songapp.presentation

import com.github.tomasz_m.songapp.domain.SongsUseCase
import com.github.tomasz_m.songapp.domain.Source
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import com.nhaarman.mockitokotlin2.*

@RunWith(MockitoJUnitRunner::class)
class SongsViewModelTest {

    @Mock
    lateinit var songsUseCase: SongsUseCase

    @Test
    fun `selecting of remote source sets requests for data from remote repository`() {
        val viewModel = SongsViewModel(songsUseCase)
        viewModel.onSelectedRadioButton(SourceFilterOptions.REMOTE)

        verify(songsUseCase, atLeastOnce()).songs(eq(Source.REMOTE), any())
    }

}