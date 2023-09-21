package com.ndmrzzzv.countriesinfo.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndmrzzzv.countriesinfo.fragment.main.data.SortType
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

    fun sort(type: SortType) {
        val listOfCountry = countries.value
        val filteredList: List<Country>? = when (type) {
            SortType.NAME_A_Z -> listOfCountry?.sortedBy { it.name }
            SortType.NAME_Z_A -> listOfCountry?.sortedByDescending { it.name }
            SortType.POPULATION_UP -> listOfCountry?.sortedBy { it.population }
            SortType.POPULATION_DOWN -> listOfCountry?.sortedByDescending { it.population }
            SortType.SURFACE_UP -> listOfCountry?.sortedBy { it.surface }
            SortType.SURFACE_DOWN -> listOfCountry?.sortedByDescending { it.surface }
        }
        _countries.value = filteredList
    }

}