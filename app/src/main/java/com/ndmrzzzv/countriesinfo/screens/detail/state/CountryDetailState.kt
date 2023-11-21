package com.ndmrzzzv.countriesinfo.screens.detail.state

import com.ndmrzzzv.domain.model.Country

data class CountryDetailState(
    val country: Country?,
    val isLoading: Boolean,
    val error: String? = null
)