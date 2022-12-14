package com.app.cocktailapp.ui.viewmodel

import androidx.lifecycle.LiveData
import com.app.cocktailapp.common.observeForTesting
import com.app.cocktailapp.ui.model.UiState
import com.app.cocktailapp.ui.splash.SplashViewModel
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest: BaseViewModelTest() {

    private lateinit var splashViewModel: SplashViewModel


    private lateinit var isOk: LiveData<UiState<Boolean>>

    @Before
    fun setUp() {
        splashViewModel = SplashViewModel()
        isOk = splashViewModel.isOk
    }

    @Test
    fun `Given output When load returns Success`() = runBlocking {

        splashViewModel.load()

        splashViewModel.isOk.observeForTesting {
            Assert.assertNotNull(splashViewModel.isOk.value)
        }
    }

    @After
    fun tearDown(){
        unmockkAll()
    }
}