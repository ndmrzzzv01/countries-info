package com.ndmrzzzv.countriesinfo.data

import com.ndmrzzzv.domain.model.Country

object CountriesForTesting {

    fun getAllCountries(): List<Country> {
        return listOf(
            Country("ATest", "", 20230,  30.0, listOf(), "Country", "","ABC", mapOf(), "", listOf(), listOf()),
            Country("BTessst", "", 100,  70.0, listOf(), "Country 2", "","TYR", mapOf(), "", listOf(), listOf()),
            Country("CTestt", "", 900,  100.0, listOf(), "Country 3", "","test", mapOf(), "", listOf(), listOf()),
            Country("DTeeest", "", 54350,  90.0, listOf(), "Country 4", "","TEST", mapOf(), "", listOf(), listOf()),
            Country("ETesstt", "", 600,  10.0, listOf(), "Country 5", "","MND", mapOf(), "", listOf(), listOf()),
            Country("FTttest", "", 870,  45.0, listOf(), "Country 6", "","test", mapOf(), "", listOf(), listOf()),
        )
    }

    val countriesSortedDescending = getAllCountries().sortedByDescending { it.name }

    val countriesSortedPopulationUp = getAllCountries().sortedBy { it.population }

    val countriesSortedSurfaceDown = getAllCountries().sortedByDescending { it.surface }

    val countriesThatContainTest = getAllCountries().filter { it.name?.lowercase()?.contains("test") == true  }

    fun countriesFilterByCode(code: String) = getAllCountries().filter { it.code == code }


}