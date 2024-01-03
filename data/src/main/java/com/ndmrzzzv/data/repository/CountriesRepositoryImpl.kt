package com.ndmrzzzv.data.repository

import com.ndmrzzzv.data.api.CountriesApi
import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.model.SortType
import com.ndmrzzzv.domain.repository.CountriesRepository

class CountriesRepositoryImpl(
    private val countriesApi: CountriesApi
) : CountriesRepository {

    override suspend fun getAllCountries(): List<Country> {
        return countriesApi.getAllCountries().map { it.convert() }
    }

    override suspend fun searchCountryByCode(code: String): List<Country> {
        return countriesApi.searchCountryByCode(code).map { it.convert() }
    }

}