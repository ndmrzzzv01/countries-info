package com.ndmrzzzv.countriesinfo.ui.screens.main

import com.ndmrzzzv.countriesinfo.data.CountriesForTesting
import com.ndmrzzzv.countriesinfo.ui.screens.main.state.CountriesState
import com.ndmrzzzv.countriesinfo.utils.InternetChecker
import com.ndmrzzzv.countriesinfo.utils.callPrivateMethod
import com.ndmrzzzv.countriesinfo.utils.getPrivateField
import com.ndmrzzzv.domain.model.SortType
import com.ndmrzzzv.domain.usecase.GetAllCountriesUseCase
import com.ndmrzzzv.domain.usecase.SortAndFilterCountriesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.never

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MainListViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Mock
    private lateinit var mockGetAllCountriesUseCase: GetAllCountriesUseCase

    @Mock
    private lateinit var mockSortAndFilterCountriesUseCase: SortAndFilterCountriesUseCase

    @Mock
    private lateinit var mockInternetChecker: InternetChecker

    private lateinit var mockViewModel: MainListViewModel

    @Before
    fun setup() {
        mockViewModel = MainListViewModel(
            mockGetAllCountriesUseCase,
            mockSortAndFilterCountriesUseCase,
            mockInternetChecker,
            dispatcher
        )
    }

    @Test
    fun initialState_isProduced() {
        val state = mockViewModel.sortedCountries.value
        Assert.assertEquals(CountriesState.Loading, state)
    }

    @Test
    fun getAllCountries_noInternet() = scope.runTest {
        `when`(mockInternetChecker.checkConnection()).thenReturn(false)

        mockViewModel.getAllCountries()

        val state = mockViewModel.sortedCountries.value
        val failedState = state as? CountriesState.LoadingFailed

        Assert.assertNotNull(failedState)
        Assert.assertEquals("You don`t have internet connection", failedState?.message)
        verify(mockGetAllCountriesUseCase, never()).invoke()
    }

    @Test
    fun getAllCountries_success() = scope.runTest {
        val countries = CountriesForTesting.getAllCountries()
        `when`(mockInternetChecker.checkConnection()).thenReturn(true)
        `when`(mockGetAllCountriesUseCase()).thenReturn(countries)

        mockViewModel.getAllCountries()

        advanceUntilIdle()

        val state = mockViewModel.sortedCountries.value
        val successState = state as? CountriesState.LoadedData

        Assert.assertNotNull(successState)
        Assert.assertEquals(countries, successState?.listOfCountries)
        verify(mockGetAllCountriesUseCase, times(1)).invoke()
    }

    @Test
    fun ensureCountriesFlowIsInitialized() {
        val viewModel = MainListViewModel::class.java

        val countriesFlowVariable = viewModel.getDeclaredField("_sortedCountries")
        countriesFlowVariable.isAccessible = true
        val countriesFlow =
            countriesFlowVariable.get(mockViewModel) as MutableStateFlow<CountriesState>

        Assert.assertEquals(CountriesState.Loading, countriesFlow.value)
    }

    @Test
    fun applySort_reflection() = scope.runTest {
        val countries = CountriesForTesting.getAllCountries()
        val searchText = "Test"
        val sortType = SortType.NAME_Z_A

        `when`(mockInternetChecker.checkConnection()).thenReturn(true)
        `when`(mockGetAllCountriesUseCase()).thenReturn(countries)

        mockViewModel.getAllCountries()

        val sortedCountriesFlow =
            mockViewModel.getPrivateField<CountriesState>("_sortedCountries")

        val searchTextFlow = mockViewModel.getPrivateField<String>("_searchText")
        searchTextFlow.value = searchText

        val sortTypeFlow = mockViewModel.getPrivateField<SortType?>("_sortType")
        sortTypeFlow.value = sortType

        mockViewModel.callPrivateMethod("applySort")

//        # 1
        val state = sortedCountriesFlow.value
        Assert.assertTrue(state is CountriesState.LoadedData)

//        # 2
        val loadedState = state as? CountriesState.LoadedData
        Assert.assertNotNull(loadedState)

//        # 3
        Assert.assertEquals(searchText, searchTextFlow.value)

//        # 4
        Assert.assertEquals(sortType, sortTypeFlow.value)
    }

}