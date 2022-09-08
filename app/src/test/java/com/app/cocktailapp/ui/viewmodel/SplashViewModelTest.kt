package com.app.cocktailapp.ui.viewmodel

import androidx.lifecycle.LiveData
import com.app.cocktailapp.core.Resource
import com.app.cocktailapp.observeForTesting
import com.app.cocktailapp.ui.splash.SplashViewModel
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest: BaseViewModelTest() {

    private lateinit var splashViewModel: SplashViewModel


    private lateinit var isOk: LiveData<Resource<Boolean>>

    @Before
    fun setUp() {
        splashViewModel = SplashViewModel()
        isOk = splashViewModel.isOk
    }

    @Test
    fun `Given output When load returns Success`() = runBlocking {
        //WHEN
        splashViewModel.load()

        //THEN
        splashViewModel.isOk.observeForTesting {
            splashViewModel.isOk.value?.data?.let { assert(it) }
        }
    }

    @After
    fun tearDown(){
        unmockkAll()
    }
}