package com.ndmrzzzv.domain.repository

import com.ndmrzzzv.domain.model.Country

interface CountriesRepository {

    suspend fun getAllCountries(): List<Country>

    suspend fun searchCountryByCode(code: String): Country

}