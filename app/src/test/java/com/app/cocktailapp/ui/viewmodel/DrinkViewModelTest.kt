package com.app.cocktailapp.ui.viewmodel

import android.content.Context
import com.app.cocktailapp.data.MockResp
import com.app.cocktailapp.data.strCategory
import com.app.cocktailapp.domain.usecase.DrinksUseCase
import com.app.cocktailapp.domain.usecase.FilterUseCase
import com.app.cocktailapp.ui.home.DrinksViewModel
import com.app.cocktailapp.ui.mapper.DrinksMapperUI
import com.app.cocktailapp.ui.mapper.ErrorMapperUI
import com.app.cocktailapp.ui.mapper.FilterMapperUI
import com.app.cocktailapp.ui.model.DrinkInfo
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class DrinkViewModelTest: BaseViewModelTest() {

    private val getDrinkFilterUseCase = mockk<FilterUseCase>()
    private val filterMapperUI = FilterMapperUI()

    private val getDrinksUseCase = mockk<DrinksUseCase>()
    private val drinkMapperUI = DrinksMapperUI()

    private val errorMapperUI = ErrorMapperUI()

    private val mContextMock = mockk<Context>()
    private val objMockk = mockk<DrinkInfo>()
    private val objErrorMockk = mockk<Error>()

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
        coEvery { getDrinkFilterUseCase.getFilters() } returns MockResp.getFilterResourceData()

        drinksViewModel.fetchDrinkFilter()

        Assert.assertNotNull(drinksViewModel.getFilterUiState.value)
    }

    @Test
    fun `Given response data when getDrinkFilter expect result contains error`() = runTest {
        coEvery { getDrinkFilterUseCase.getFilters() } returns MockResp.getFilterFailureMock()

        drinksViewModel.fetchDrinkFilter()

        Assert.assertNotNull(drinksViewModel.getFilterUiState.value)
    }

    @Test
    fun `Given filter category data when setDrinkCategory expect result set category filter`() = runTest {
        coEvery { getDrinkFilterUseCase.getFilters() } returns MockResp.getFilterResourceData()

        drinksViewModel.setDrinkCategory(strCategory)

        Assert.assertNotNull(strCategory)
    }

    @Test
    fun `Given response data when getDrinks expect result contains drinks data`() = runTest {
        coEvery { getDrinksUseCase.fetchDrinksByCategory(strCategory) } returns MockResp.getDrinksResourceData()

        drinksViewModel.fetchDrinks(strCategory)

        Assert.assertNotNull(drinksViewModel.getDrinkUiState.value)
    }

    @Test
    fun `Given response data when getDrinks expect result contains error`() = runTest {
        coEvery { getDrinksUseCase.fetchDrinksByCategory(strCategory) } returns MockResp.getDrinksFailureMock()

        drinksViewModel.fetchDrinks(strCategory)

        Assert.assertNotNull(drinksViewModel.getDrinkUiState.value)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}