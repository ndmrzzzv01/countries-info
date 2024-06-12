package com.ndmrzzzv.countriesinfo.ui.screens.main

import com.ndmrzzzv.countriesinfo.data.CountriesForTesting
import com.ndmrzzzv.domain.model.SortType
import com.ndmrzzzv.domain.usecase.SortAndFilterCountriesUseCase
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class SortAndFilterCountriesUseCaseTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private val sortAndFilterCountriesUseCase = SortAndFilterCountriesUseCase()

    @Test
    fun getAllCountries_setTypeSort() = scope.runTest {
        val countries = CountriesForTesting.getAllCountries()

        val expectedCountries = CountriesForTesting.countriesSortedDescending
        val actualCountries = sortAndFilterCountriesUseCase.invoke(countries, SortType.NAME_Z_A, "")

        Assert.assertEquals(expectedCountries, actualCountries)
    }

    @Test
    fun getAllCountries_setSearchQuery() = scope.runTest {
        val countries = CountriesForTesting.getAllCountries()

        val expectedCountries = CountriesForTesting.countriesThatContainTest
        val actualCountries = sortAndFilterCountriesUseCase.invoke(countries, null, "Test")

        Assert.assertEquals(expectedCountries, actualCountries)
    }

}