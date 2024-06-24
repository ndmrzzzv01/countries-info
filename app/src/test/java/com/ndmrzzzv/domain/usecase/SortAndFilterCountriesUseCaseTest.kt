package com.ndmrzzzv.domain.usecase

import com.ndmrzzzv.countriesinfo.data.CountriesForTesting
import com.ndmrzzzv.domain.model.SortType
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class SortAndFilterCountriesUseCaseTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private val sortAndFilterCountriesUseCase = SortAndFilterCountriesUseCase()

    @Test
    fun `get all countries with type sort NAME_Z_A`() = scope.runTest {
        val countries = CountriesForTesting.getAllCountries()

        val expectedCountries = CountriesForTesting.getAllCountries().sortedByDescending { it.name }
        val actualCountries = sortAndFilterCountriesUseCase.invoke(countries, SortType.NAME_Z_A, "")

        assertEquals(expectedCountries, actualCountries)
    }

    @Test
    fun `get all countries with type sort POPULATION_UP`() = scope.runTest {
        val countries = CountriesForTesting.getAllCountries()

        val expectedCountries = CountriesForTesting.getAllCountries().sortedBy { it.population }
        val actualCountries = sortAndFilterCountriesUseCase.invoke(countries, SortType.POPULATION_UP, "")

        assertEquals(expectedCountries, actualCountries)
    }

    @Test
    fun `get all countries with type sort SURFACE_DOWN`() = scope.runTest {
        val countries = CountriesForTesting.getAllCountries()

        val expectedCountries = CountriesForTesting.getAllCountries().sortedByDescending { it.surface }
        val actualCountries = sortAndFilterCountriesUseCase.invoke(countries, SortType.SURFACE_DOWN, "")

        assertEquals(expectedCountries, actualCountries)
    }

    @Test
    fun `get all countries with search query`() = scope.runTest {
        val countries = CountriesForTesting.getAllCountries()

        val expectedCountries = CountriesForTesting.getAllCountries()
            .filter { it.name?.lowercase()?.contains("country 2") == true  }
        val actualCountries = sortAndFilterCountriesUseCase.invoke(countries, null, "country 2")

        assertEquals(expectedCountries, actualCountries)
    }

    @Test
    fun `get all countries with search query and sort type NAME_Z_A`() = scope.runTest {
        val countries = CountriesForTesting.getAllCountries()

        val expectedCountries = CountriesForTesting.getAllCountries()
            .filter { it.name?.lowercase()?.contains("country 2") == true  }.sortedByDescending { it.name }
        val actualCountries = sortAndFilterCountriesUseCase.invoke(countries, SortType.NAME_Z_A, "Country 2")

        assertEquals(expectedCountries, actualCountries)
    }

}