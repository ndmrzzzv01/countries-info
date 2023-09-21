package com.ndmrzzzv.data.repository

import com.ndmrzzzv.data.api.CountriesApi
import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.repository.CountriesRepository

class CountriesRepositoryImpl(
    private val countriesApi: CountriesApi
) : CountriesRepository {

    override suspend fun getAllCountries(): List<Country> {
        return countriesApi.getAllCountries().map { country ->
            Country(
                country.name?.common,
                country.flags?.png,
                country.population,
                country.area,
                country.capital,
                country.name?.official,
                country.coatOfArms?.png,
//                country.languages,
                country.maps?.googleMaps,
                country.timezones,
                country.borders
            )
        }
    }

    override suspend fun searchCountryByName(name: String): Any {
        TODO("Not yet implemented")
    }

    override suspend fun searchCountryByCode(code: String): Any {
        TODO("Not yet implemented")
    }

}