package com.ndmrzzzv.countriesinfo.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndmrzzzv.countriesinfo.feature.InternetChecker
import com.ndmrzzzv.countriesinfo.fragment.main.data.SortType
import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.usecase.GetAllCountriesUseCase
import kotlinx.coroutines.launch

class MainListViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val internetChecker: InternetChecker
) : ViewModel() {

    private val _countries = MutableLiveData<List<Country>?>()
    val countries: LiveData<List<Country>?> = _countries

//    private val _sortType = MutableLiveData<List<Country>?>()
//    val sortType: LiveData<List<Country>?> = _sortType
//
//    private val _searchText = MutableLiveData<List<Country>?>()
//    val searchText: LiveData<List<Country>?> = _searchText

    private var listOfAllCountries = listOf<Country>()

    fun getAllCountries() {
        viewModelScope.launch {
            if (internetChecker.checkConnection()) {
                val result = getAllCountriesUseCase.invoke()
                _countries.value = result
                listOfAllCountries = result
            } else {
                _countries.value = null
            }
        }
    }

    fun setSortType(type: SortType) {
//        sortType.value = type
//        applySort()

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

    fun setSearchText(searchText: String) {
//        searchText.value = searchTextString
//        applySort()

        viewModelScope.launch {
            _countries.value = if (searchText.isNotEmpty()) countries.value?.filter {
                it.name?.lowercase()?.contains(searchText.lowercase()) == true
            }
            else listOfAllCountries
        }
    }

//    private fun applySort() {
//        viewModelScope.launch {
//
//        }
//    }

}