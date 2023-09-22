package com.ndmrzzzv.countriesinfo.fragment.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.usecase.SearchCountriesByCodeUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val searchCountriesByCodeUseCase: SearchCountriesByCodeUseCase
) : ViewModel() {

    private val _country = MutableLiveData<List<Country>>()
    val country: LiveData<List<Country>> = _country

    fun getInfoAboutCountry(code: String) {
        viewModelScope.launch {
            _country.value = searchCountriesByCodeUseCase.invoke(code)
        }
    }

}