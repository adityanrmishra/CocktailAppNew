package com.app.cocktailapp.ui.viewmodel

import android.content.Context
import com.app.cocktailapp.data.MockResp
import com.app.cocktailapp.data.idDrink
import com.app.cocktailapp.domain.usecase.DrinkInfoUseCaseImp
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
class DrinkInfoInfoViewModelTest : BaseViewModelTest() {

    private val drinkInfoUseCaseImp = mockk<DrinkInfoUseCaseImp>()
    private val drinkMapperUI = DrinkMapperUI()
    private val errorMapperUI = ErrorMapperUI()

    private val drinkInfoViewModel: DrinkInfoViewModel by lazy {
        DrinkInfoViewModel(
            drinkInfoUseCaseImp,
            drinkMapperUI,
            errorMapperUI
        )
    }

    @Test
    fun `Given response when invoke drink info use case expect response contains idDrink`() =
        runTest {
            coEvery { drinkInfoUseCaseImp.getDrinkById(idDrink) } returns MockResp.getDrinkInfoResourceData()

            drinkInfoViewModel.fetchDrink(idDrink)

            Assert.assertNotNull(drinkInfoViewModel.getDrinkInfoUiState.value)
        }

    @Test
    fun `Given error when invoke details use case expect null data`() = runTest {
        coEvery { drinkInfoUseCaseImp.getDrinkById(idDrink) } returns MockResp.getDrinkInfoFailureMock()

        drinkInfoViewModel.fetchDrink(idDrink)

        Assert.assertNotNull(drinkInfoViewModel.getDrinkInfoUiState.value)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}