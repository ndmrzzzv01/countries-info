package com.ndmrzzzv.countriesinfo.ui.screens.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndmrzzzv.countriesinfo.activity.BaseViewModel
import com.ndmrzzzv.countriesinfo.utils.InternetChecker
import com.ndmrzzzv.countriesinfo.ui.screens.detail.state.CountryDetailState
import com.ndmrzzzv.countriesinfo.ui.screens.main.state.CountriesState
import com.ndmrzzzv.domain.usecase.SearchCountriesByCodeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val searchCountriesByCodeUseCase: SearchCountriesByCodeUseCase,
    private val internetChecker: InternetChecker,
    private val stateHandle: SavedStateHandle,
    private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _country = MutableStateFlow<CountryDetailState>(CountryDetailState.Loading)
    val country = _country.asStateFlow()

    init {
        loadInfoAboutCountry()
    }

    fun loadInfoAboutCountry() {
        val code = stateHandle.get<String>("country_code") ?: ""
        scopeWithExceptionHandler.launch(dispatcher) {
            _country.value = CountryDetailState.Loading
            _country.value = if (internetChecker.checkConnection()) {
                CountryDetailState.LoadedData(searchCountriesByCodeUseCase(code).getOrNull(0))
            } else {
                CountryDetailState.LoadingFailed("You don`t have Internet connection")
            }
        }
    }

    fun openGoogleMapLink(context: Context, url: String?) {
        if (url != null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
    }

}