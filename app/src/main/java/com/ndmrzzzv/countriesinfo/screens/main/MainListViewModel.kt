package com.ndmrzzzv.countriesinfo.screens.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndmrzzzv.countriesinfo.feature.InternetChecker
import com.ndmrzzzv.countriesinfo.screens.main.data.SortType
import com.ndmrzzzv.countriesinfo.screens.main.state.CountriesState
import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.usecase.GetAllCountriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainListViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val internetChecker: InternetChecker
) : ViewModel() {

    private val _sortedCountries = mutableStateOf(CountriesState(listOf(), true))
    val sortedCountries: State<CountriesState> = _sortedCountries

    private val _sortType = MutableLiveData<SortType>()
    private val _searchText = MutableLiveData<String>()
    private var allCountries = listOf<Country>()

    init {
        getAllCountries()
    }

    private fun getAllCountries() {
        viewModelScope.launch {
            if (internetChecker.checkConnection()) {
                val result = getAllCountriesUseCase.invoke()
                _sortedCountries.value = _sortedCountries.value.copy(
                    countries = result, isLoading = false
                )
                allCountries = result
            } else {
                _sortedCountries.value = _sortedCountries.value.copy(
                    countries = listOf(),
                    isLoading = false,
                    error = "You don`t have Internet connection"
                )
            }
        }
    }

    fun setTypeSort(type: SortType) {
        _sortType.value = type
        applySort()
    }

    fun setSearchText(searchText: String) {
        _searchText.value = searchText
        applySort()
    }

    private fun applySort() {
        viewModelScope.launch(Dispatchers.Default) {
            var sortedList = when (_sortType.value) {
                SortType.NAME_A_Z -> allCountries.sortedBy { it.name }
                SortType.NAME_Z_A -> allCountries.sortedByDescending { it.name }
                SortType.POPULATION_UP -> allCountries.sortedBy { it.population }
                SortType.POPULATION_DOWN -> allCountries.sortedByDescending { it.population }
                SortType.SURFACE_UP -> allCountries.sortedBy { it.surface }
                SortType.SURFACE_DOWN -> allCountries.sortedByDescending { it.surface }
                else -> allCountries
            }

            val searchText = _searchText.value
            if (searchText?.isNotEmpty() == true) {
                sortedList = sortedList.filter {
                    it.name?.lowercase()?.contains(searchText.lowercase()) == true
                }
            }

            _sortedCountries.value = _sortedCountries.value.copy(
                countries = sortedList, isLoading = false
            )
        }
    }

}