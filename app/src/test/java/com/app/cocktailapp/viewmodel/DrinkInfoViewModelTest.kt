package com.app.cocktailapp.viewmodel

import android.content.Context
import com.app.cocktailapp.core.MockResponse
import com.app.cocktailapp.core.idDrink
import com.app.cocktailapp.domain.usecase.DrinkUseCase
import com.app.cocktailapp.ui.detail.DrinkInfoViewModel
import com.app.cocktailapp.ui.mapper.DrinkMapperUI
import com.app.cocktailapp.ui.mapper.ErrorMapperUI
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class DrinkInfoViewModelTest: BaseViewModelTest() {

    private val mContextMock = mockk<Context>(relaxed = true)
    private val drinkUseCase = mockk<DrinkUseCase>()
    private val drinkMapperUI = DrinkMapperUI()
    private val errorMapperUI = ErrorMapperUI(mContextMock)

    private val drinkInfoViewModel: DrinkInfoViewModel by lazy {
        DrinkInfoViewModel(
            errorMapperUI,
            drinkUseCase,
            drinkMapperUI
        )
    }

    @Test
    fun `Given response when invoke drink info use case expect response contains idDrink`() =
        runTest {
            coEvery { drinkUseCase.invoke(idDrink) } returns MockResponse.getDrinkResourceData()
            drinkInfoViewModel.fetchDrinks(idDrink)
            Assert.assertNotNull(drinkInfoViewModel.getDrinkState.value.data)
        }

    @Test
    fun `Given error when invoke details use case expect null data`() = runTest {
        coEvery { drinkUseCase.invoke(idDrink) } returns MockResponse.getDrinkFailureMock()
        drinkInfoViewModel.fetchDrinks(idDrink)
        Assert.assertNotNull(drinkInfoViewModel.getDrinkState.value.error)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}