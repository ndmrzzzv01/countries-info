package com.ndmrzzzv.countriesinfo.ui.screens.main

import com.ndmrzzzv.countriesinfo.data.CountriesForTesting
import com.ndmrzzzv.domain.repository.CountriesRepository
import com.ndmrzzzv.domain.usecase.GetAllCountriesUseCase
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class GetAllCountriesUseCaseTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private lateinit var countriesRepository: CountriesRepository
    private lateinit var getAllCountriesUseCase: GetAllCountriesUseCase

    @Before
    fun setup() {
        countriesRepository = mock()
        getAllCountriesUseCase = GetAllCountriesUseCase(countriesRepository)
    }

    @Test
    fun getAllCountries_UseCase() = scope.runTest {
        val expectedCountries = CountriesForTesting.getAllCountries()
        `when`(countriesRepository.getAllCountries()).thenReturn(expectedCountries)

        val countries = getAllCountriesUseCase.invoke()

        Assert.assertEquals(expectedCountries, countries)
    }

}