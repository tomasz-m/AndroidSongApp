package com.github.tomasz_m.songapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.tomasz_m.songapp.domain.SongsUseCase
import com.github.tomasz_m.songapp.domain.SongsUseCaseResponse
import com.github.tomasz_m.songapp.domain.Source
import com.github.tomasz_m.songapp.domain.Status
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SongsViewModelTest {

    @Mock
    lateinit var songsUseCase: SongsUseCase


    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun `selecting of remote source does not call remote repository when data was never accessed`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val viewModel = SongsViewModel(songsUseCase)

            viewModel.onSelectedRadioButton(SourceFilterOptions.REMOTE)

            verify(songsUseCase, times(0)).songs(eq(Source.REMOTE))
        }

    @Test
    fun `selecting of remote source calls remote repository after data was accessed`()  = coroutinesTestRule.testDispatcher.runBlockingTest {
        val viewModel = SongsViewModel(songsUseCase)
        whenever(songsUseCase.songs(any())).thenReturn(SongsUseCaseResponse(emptyList(), Status.OK))

        @Suppress("UNUSED_VARIABLE") val ignored = viewModel.songs
        reset(songsUseCase)

        viewModel.onSelectedRadioButton(SourceFilterOptions.REMOTE)

        verify(songsUseCase, times(1)).songs(eq(Source.REMOTE))
    }


    @ExperimentalCoroutinesApi
    class CoroutinesTestRule(
        val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    ) : TestWatcher() {

        override fun starting(description: Description?) {
            super.starting(description)
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description?) {
            super.finished(description)
            Dispatchers.resetMain()
            testDispatcher.cleanupTestCoroutines()
        }
    }


}