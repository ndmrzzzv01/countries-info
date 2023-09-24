package com.ndmrzzzv.countriesinfo.fragment.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndmrzzzv.countriesinfo.feature.InternetChecker
import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.usecase.SearchCountriesByCodeUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val searchCountriesByCodeUseCase: SearchCountriesByCodeUseCase,
    private val internetChecker: InternetChecker
) : ViewModel() {

    private val _country = MutableLiveData<Country?>()
    val country: LiveData<Country?> = _country

    fun getInfoAboutCountry(code: String) {
        viewModelScope.launch {
            if (internetChecker.checkConnection()) {
                _country.value = searchCountriesByCodeUseCase.invoke(code).getOrNull(0)
            } else {
                _country.value = null
            }
        }
    }

}