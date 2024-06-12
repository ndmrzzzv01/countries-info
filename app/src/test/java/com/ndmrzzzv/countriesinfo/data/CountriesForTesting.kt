package com.ndmrzzzv.countriesinfo.data

import com.ndmrzzzv.domain.model.Country

object CountriesForTesting {

    fun getAllCountries(): List<Country> {
        return listOf(
            Country("ATest", "", 200,  30.0, listOf(), "Country", "","", mapOf(), "", listOf(), listOf()),
            Country("BTessst", "", 300,  30.0, listOf(), "Country 2", "","", mapOf(), "", listOf(), listOf()),
            Country("CTestt", "", 400,  30.0, listOf(), "Country 3", "","", mapOf(), "", listOf(), listOf()),
            Country("DTeeest", "", 500,  30.0, listOf(), "Country 4", "","", mapOf(), "", listOf(), listOf()),
            Country("ETesstt", "", 600,  30.0, listOf(), "Country 5", "","", mapOf(), "", listOf(), listOf()),
            Country("FTttest", "", 700,  30.0, listOf(), "Country 6", "","", mapOf(), "", listOf(), listOf()),
        )
    }

    val countriesSortedDescending = getAllCountries().sortedByDescending { it.name }

    val countriesThatContainTest = getAllCountries().filter { it.name?.lowercase()?.contains("test") == true  }

}