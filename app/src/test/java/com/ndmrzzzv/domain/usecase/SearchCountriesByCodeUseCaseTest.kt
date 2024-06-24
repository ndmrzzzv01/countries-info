package com.ndmrzzzv.domain.usecase

import com.ndmrzzzv.countriesinfo.data.CountriesForTesting
import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.repository.CountriesRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SearchCountriesByCodeUseCaseTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private lateinit var mockCountriesRepository: CountriesRepository
    private lateinit var searchCountriesByCodeUseCase: SearchCountriesByCodeUseCase

    @Before
    fun setup() {
        mockCountriesRepository = mock()
        searchCountriesByCodeUseCase = SearchCountriesByCodeUseCase(mockCountriesRepository)
    }

    @Test
    fun `get result by code`() = scope.runTest {
        val code = "country"
        val expectedResult = listOf(Country("Country 3", "", 900,  100.0, listOf(), "Country 3", "","test", mapOf(), "", listOf(), listOf()))

        Mockito.`when`(mockCountriesRepository.searchCountryByCode(code)).thenReturn(expectedResult)

        val country = searchCountriesByCodeUseCase.invoke(code)

        assertEquals(expectedResult, country)
    }

    @Test
    fun `get result by empty code`() = scope.runTest {
        val code = ""
        val expectedResult = CountriesForTesting.getAllCountries().filter { it.code == code }
        Mockito.`when`(mockCountriesRepository.searchCountryByCode(code)).thenReturn(expectedResult)

        val country = searchCountriesByCodeUseCase.invoke(code)

        assertEquals(expectedResult, country)
    }

}