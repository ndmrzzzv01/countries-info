package com.ndmrzzzv.domain.usecase

import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.repository.CountriesRepository

class SearchCountriesByCodeUseCase(
    private val countriesRepository: CountriesRepository
) {

    suspend operator fun invoke(code: String): List<Country> {
        return countriesRepository.searchCountryByCode(code)
    }

}