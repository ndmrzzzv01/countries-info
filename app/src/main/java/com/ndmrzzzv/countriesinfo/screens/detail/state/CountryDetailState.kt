package com.ndmrzzzv.countriesinfo.screens.detail.state

import com.ndmrzzzv.domain.model.Country

sealed class CountryDetailState {

    class LoadingFailed(val message: String) : CountryDetailState()

    class LoadedData(val country: Country?) : CountryDetailState()

    object Loading : CountryDetailState()

}