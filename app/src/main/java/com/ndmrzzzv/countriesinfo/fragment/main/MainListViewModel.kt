package com.ndmrzzzv.countriesinfo.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.usecase.GetAllCountriesUseCase
import kotlinx.coroutines.launch

class MainListViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
) : ViewModel() {

    private val _countries = MutableLiveData<List<Country>?>()
    val countries: LiveData<List<Country>?> = _countries

    init {
        viewModelScope.launch {
            _countries.value = getAllCountriesUseCase.invoke()
        }
    }

}