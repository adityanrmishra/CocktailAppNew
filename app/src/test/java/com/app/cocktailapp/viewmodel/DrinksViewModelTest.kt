package com.app.cocktailapp.viewmodel

import android.content.Context
import com.app.cocktailapp.core.MockResponse
import com.app.cocktailapp.core.strCategory
import com.app.cocktailapp.domain.usecase.DrinkFilterUseCase
import com.app.cocktailapp.domain.usecase.DrinksUseCase
import com.app.cocktailapp.ui.home.DrinksViewModel
import com.app.cocktailapp.ui.mapper.DrinkMapperUI
import com.app.cocktailapp.ui.mapper.ErrorMapperUI
import com.app.cocktailapp.ui.mapper.FilterMapperUI
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class DrinksViewModelTest: BaseViewModelTest() {

    private val getDrinkFilterUseCase = mockk<DrinkFilterUseCase>()
    private val filterMapperUI = FilterMapperUI()

    private val getDrinksUseCase = mockk<DrinksUseCase>()
    private val drinkMapperUI = DrinkMapperUI()

    private val mContextMock = mockk<Context>(relaxed = true)
    private val errorMapperUI = ErrorMapperUI(mContextMock)

    private val drinksViewModel: DrinksViewModel by lazy {
        DrinksViewModel(
            getDrinkFilterUseCase,
            filterMapperUI,
            getDrinksUseCase,
            drinkMapperUI,
            errorMapperUI
        )
    }

    @Test
    fun `Given response data when getDrinkFilter expect result contains filter data`() = runTest {
        coEvery { getDrinkFilterUseCase.invoke() } returns MockResponse.getFilterResourceData()
        drinksViewModel.fetchDrinkFilter()
        Assert.assertNotNull(drinksViewModel.getFilterState.value.data)
    }

    @Test
    fun `Given response data when getDrinkFilter expect result contains error`() = runTest {
        coEvery { getDrinkFilterUseCase.invoke() } returns MockResponse.getFilterFailureMock()
        drinksViewModel.fetchDrinkFilter()
        Assert.assertNotNull(drinksViewModel.getFilterState.value.error)
    }

    @Test
    fun `Given response data when getDrinks expect result contains drinks data`() = runTest {
        coEvery { getDrinksUseCase.invoke(strCategory) } returns MockResponse.getDrinksResourceData()
        drinksViewModel.setDrinkCategory(strCategory)
        drinksViewModel.fetchDrinks()
        Assert.assertNotNull(drinksViewModel.getDrinksState.value.data)
    }

    @Test
    fun `Given response data when getDrinks expect result contains error`() = runTest {
        coEvery { getDrinksUseCase.invoke(strCategory) } returns MockResponse.getDrinksFailureMock()
        drinksViewModel.setDrinkCategory(strCategory)
        drinksViewModel.fetchDrinks()
        Assert.assertNotNull(drinksViewModel.getDrinksState.value.error)
    }

    @Test
    fun `Given filter category data when setDrinkCategory expect result set category filter`() = runTest {
        coEvery { getDrinkFilterUseCase.invoke() } returns MockResponse.getFilterResourceData()
        drinksViewModel.setDrinkCategory(strCategory)
        Assert.assertNotNull(strCategory)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}