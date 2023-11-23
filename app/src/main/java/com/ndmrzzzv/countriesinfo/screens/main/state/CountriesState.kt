package com.ndmrzzzv.countriesinfo.screens.main.state

import com.ndmrzzzv.domain.model.Country

sealed class CountriesState {
    class LoadingFailed(val message: String) : CountriesState()

    class LoadedData(val listOfCountries: List<Country>) : CountriesState()

    object Loading : CountriesState()
}
