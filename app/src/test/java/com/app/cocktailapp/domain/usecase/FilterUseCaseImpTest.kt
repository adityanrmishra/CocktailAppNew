package com.app.cocktailapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.cocktailapp.common.TestCoroutineRule
import com.app.cocktailapp.data.MockResp
import com.app.cocktailapp.data.errorMessage
import com.app.cocktailapp.domain.repository.FilterRepositoryImp
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
class FilterUseCaseImpTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val filterRepositoryImp = mockk<FilterRepositoryImp>(relaxed = true)

    lateinit var filterUseCaseImp: FilterUseCaseImp

    @Before
    fun setUp() {
        filterUseCaseImp = FilterUseCaseImp(filterRepositoryImp)
    }

    @Test
    fun `Given filters When getFilterData of FilterUseCaseImpl returns Success`() = runTest {
        coEvery { filterUseCaseImp.getFilters() }.returns(MockResp.getFilterResourceData())
        val data = MockResp.getFilterData().drinks
        Assert.assertEquals("Ordinary Drink", data[0].strCategory)
    }

    @Test
    fun `Given Filters When getFilterData FilterUseCaseImpl returns Error`() = runTest {
        coEvery { filterUseCaseImp.getFilters() } returns MockResp.getFilterFailureMock()
        val data = MockResp.getFilterFailureMock().first()
        Assert.assertEquals(errorMessage, data.message)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}