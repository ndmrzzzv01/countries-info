package com.ndmrzzzv.countriesinfo.ui.screens.main


import com.ndmrzzzv.countriesinfo.data.CountriesForTesting
import com.ndmrzzzv.countriesinfo.ui.screens.main.state.CountriesState
import com.ndmrzzzv.countriesinfo.utils.InternetChecker
import com.ndmrzzzv.domain.model.SortType
import com.ndmrzzzv.domain.usecase.GetAllCountriesUseCase
import com.ndmrzzzv.domain.usecase.SortAndFilterCountriesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class MainListViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private lateinit var getAllCountriesUseCase: GetAllCountriesUseCase
    private lateinit var sortAndFilterCountriesUseCase: SortAndFilterCountriesUseCase
    private lateinit var internetChecker: InternetChecker
    private lateinit var viewModel: MainListViewModel

    @Before
    fun setup() {
        getAllCountriesUseCase = mock()
        sortAndFilterCountriesUseCase = mock()
        internetChecker = mock()
        viewModel = MainListViewModel(
            getAllCountriesUseCase,
            sortAndFilterCountriesUseCase,
            internetChecker,
            dispatcher
        )
    }

    @Test
    fun initialState_isProduced() {
        val state = viewModel.sortedCountries.value
        Assert.assertEquals(CountriesState.Loading, state)
    }

    @Test
    fun getAllCountries_noInternet() = scope.runTest {
        `when`(internetChecker.checkConnection()).thenReturn(false)
        viewModel.getAllCountries()
        val state = viewModel.sortedCountries.value
        val failedState = state as? CountriesState.LoadingFailed

        Assert.assertNotNull(failedState)
        Assert.assertEquals("You don`t have internet connection", failedState?.message)
    }

    @Test
    fun getAllCountries_success() = scope.runTest {
        val countries = CountriesForTesting.getAllCountries()
        `when`(internetChecker.checkConnection()).thenReturn(true)
        `when`(getAllCountriesUseCase()).thenReturn(countries)

        viewModel.getAllCountries()

        advanceUntilIdle()

        val state = viewModel.sortedCountries.value
        val successState = state as? CountriesState.LoadedData
        Assert.assertNotNull(successState)
        Assert.assertEquals(countries, successState?.listOfCountries)
    }

    @Test
    fun allCountries_setTypeSort() = scope.runTest {
        val countries = CountriesForTesting.getAllCountries()
        val sortedCountries = CountriesForTesting.countriesSortedDescending

        `when`(internetChecker.checkConnection()).thenReturn(true)
        `when`(getAllCountriesUseCase()).thenReturn(countries)
        `when`(sortAndFilterCountriesUseCase(countries, SortType.NAME_Z_A, "")).thenReturn(sortedCountries)

        viewModel.getAllCountries()
        advanceUntilIdle()

        viewModel.setTypeSort(SortType.NAME_Z_A)

        val state = viewModel.sortedCountries.value
        val successState = state as? CountriesState.LoadedData
        Assert.assertNotNull(successState)
        Assert.assertEquals(sortedCountries, successState?.listOfCountries)
    }

    @Test
    fun allCountries_setSearchText() = scope.runTest {
        val countries = CountriesForTesting.getAllCountries()
        val filteredCountries = CountriesForTesting.countriesThatContainTest

        `when`(internetChecker.checkConnection()).thenReturn(true)
        `when`(getAllCountriesUseCase()).thenReturn(countries)
        `when`(sortAndFilterCountriesUseCase(countries, null, "Test")).thenReturn(filteredCountries)

        viewModel.getAllCountries()
        advanceUntilIdle()

        viewModel.setSearchText("Test")

        val state = viewModel.sortedCountries.value
        val successState = state as? CountriesState.LoadedData
        Assert.assertNotNull(successState)
        Assert.assertEquals(filteredCountries, successState?.listOfCountries)
    }

}