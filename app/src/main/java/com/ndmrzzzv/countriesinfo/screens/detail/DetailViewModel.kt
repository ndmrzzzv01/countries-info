package com.ndmrzzzv.countriesinfo.screens.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndmrzzzv.countriesinfo.feature.InternetChecker
import com.ndmrzzzv.countriesinfo.screens.detail.state.CountryDetailState
import com.ndmrzzzv.domain.usecase.SearchCountriesByCodeUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val searchCountriesByCodeUseCase: SearchCountriesByCodeUseCase,
    private val internetChecker: InternetChecker,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private val _country = mutableStateOf(CountryDetailState(null, true))
    val country: State<CountryDetailState> = _country

    init {
        val code = stateHandle.get<String>("country_code") ?: ""
        viewModelScope.launch {
            _country.value = if (internetChecker.checkConnection()) {
                _country.value.copy(
                    country = searchCountriesByCodeUseCase.invoke(code).getOrNull(0),
                    isLoading = false
                )
            } else {
                _country.value.copy(
                    country = null,
                    isLoading = false,
                    error = "You don`t have Internet connection"
                )
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