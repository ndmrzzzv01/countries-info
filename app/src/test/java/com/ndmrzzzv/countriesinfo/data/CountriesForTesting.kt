package com.ndmrzzzv.countriesinfo.data

import com.ndmrzzzv.domain.model.Country

object CountriesForTesting {

    fun getAllCountries(): List<Country> {
        return listOf(
            Country("Country 1", "", 20230,  30.0, listOf(), "Country 1", "","ABC", mapOf(), "", listOf(), listOf()),
            Country("Country 2", "", 100,  70.0, listOf(), "Country 2", "","TYR", mapOf(), "", listOf(), listOf()),
            Country("Country 3", "", 900,  100.0, listOf(), "Country 3", "","test", mapOf(), "", listOf(), listOf()),
            Country("Country 4", "", 54350,  90.0, listOf(), "Country 4", "","TEST", mapOf(), "", listOf(), listOf()),
            Country("Country 5", "", 600,  10.0, listOf(), "Country 5", "","MND", mapOf(), "", listOf(), listOf()),
            Country("Country 6", "", 870,  45.0, listOf(), "Country 6", "","test", mapOf(), "", listOf(), listOf()),
        )
    }

}