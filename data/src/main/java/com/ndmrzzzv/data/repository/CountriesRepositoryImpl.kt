package com.ndmrzzzv.data.repository

import com.ndmrzzzv.data.api.CountriesApi
import com.ndmrzzzv.data.features.convert
import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.repository.CountriesRepository

class CountriesRepositoryImpl(
    private val countriesApi: CountriesApi
) : CountriesRepository {

    override suspend fun getAllCountries(): List<Country> {
        return countriesApi.getAllCountries().convert()
    }

    override suspend fun searchCountryByCode(code: String): Any {
        TODO("Not yet implemented")
    }

}