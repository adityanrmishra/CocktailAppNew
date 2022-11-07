package com.app.cocktailapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.cocktailapp.common.TestCoroutineRule
import com.app.cocktailapp.data.MockResp
import com.app.cocktailapp.data.errorMessage
import com.app.cocktailapp.domain.repository.DrinkRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class FilterUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val filterRepositoryImp = mockk<DrinkRepository>()

    lateinit var filterUseCase: FilterUseCase

    @Before
    fun setUp() {
        filterUseCase = FilterUseCase(filterRepositoryImp)
    }

    @Test
    fun `Given filters When getFilterData of FilterUseCaseImpl returns Success`() = runTest {
        coEvery { filterUseCase.getFilters() }.returns(MockResp.getFilterResourceData())

        val data = MockResp.getFilterData().drinks

        Assert.assertEquals("Ordinary Drink", data.first().strCategory)
    }

    @Test
    fun `Given Filters When getFilterData FilterUseCaseImpl returns Error`() = runTest {
        coEvery { filterUseCase.getFilters() } returns MockResp.getFilterFailureMock()

        val data = MockResp.getFilterFailureMock().first()

        Assert.assertEquals(errorMessage, data.message)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}