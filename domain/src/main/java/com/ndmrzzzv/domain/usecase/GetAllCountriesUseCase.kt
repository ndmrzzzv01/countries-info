package com.ndmrzzzv.domain.usecase

import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.repository.CountriesRepository

class GetAllCountriesUseCase(
    private val countriesRepository: CountriesRepository
) {

    suspend operator fun invoke(): List<Country> {
        return countriesRepository.getAllCountries()
    }

}