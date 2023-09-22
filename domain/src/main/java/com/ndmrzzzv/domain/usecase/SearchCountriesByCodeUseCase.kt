package com.ndmrzzzv.domain.usecase

import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.repository.CountriesRepository

class SearchCountriesByCodeUseCase(
    private val countriesRepository: CountriesRepository
) {

    suspend fun invoke(code: String): Country {
        return countriesRepository.searchCountryByCode(code)
    }

}