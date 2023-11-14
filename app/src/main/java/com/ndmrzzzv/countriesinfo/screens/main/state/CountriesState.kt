package com.ndmrzzzv.countriesinfo.screens.main.state

import com.ndmrzzzv.domain.model.Country

data class CountriesState(
    val countries: List<Country>,
    val isLoading: Boolean,
    val error: String? = null
)