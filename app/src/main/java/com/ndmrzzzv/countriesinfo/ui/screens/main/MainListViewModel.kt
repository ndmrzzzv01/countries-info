package com.ndmrzzzv.countriesinfo.ui.screens.main

import com.ndmrzzzv.countriesinfo.activity.BaseViewModel
import com.ndmrzzzv.countriesinfo.ui.screens.main.state.CountriesState
import com.ndmrzzzv.countriesinfo.utils.InternetChecker
import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.model.SortType
import com.ndmrzzzv.domain.usecase.GetAllCountriesUseCase
import com.ndmrzzzv.domain.usecase.SortAndFilterCountriesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainListViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val sortAndFilterCountriesUseCase: SortAndFilterCountriesUseCase,
    private val internetChecker: InternetChecker,
    private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _sortedCountries = MutableStateFlow<CountriesState>(CountriesState.Loading)
    val sortedCountries = _sortedCountries.asStateFlow()

    private val _sortType = MutableStateFlow<SortType?>(null)
    private val _searchText = MutableStateFlow("")
    private var allCountries = listOf<Country>()

    init {
        getAllCountries()
    }

    fun getAllCountries() {
        scopeWithExceptionHandler.launch(dispatcher) {
            _sortedCountries.value = CountriesState.Loading
            if (internetChecker.checkConnection()) {
                val result = getAllCountriesUseCase()
                _sortedCountries.value = CountriesState.LoadedData(result, _searchText.value)
                allCountries = result
            } else {
                _sortedCountries.value =
                    CountriesState.LoadingFailed("You don`t have internet connection")
            }
        }
    }

    fun setTypeSort(type: SortType?) {
        _sortType.value = type
        applySort()
    }

    fun setSearchText(searchText: String) {
        _searchText.value = searchText
        applySort()
    }

    private fun applySort() {
        val result = sortAndFilterCountriesUseCase(allCountries, _sortType.value, _searchText.value)
        _sortedCountries.value = CountriesState.LoadedData(result, _searchText.value)
    }

}