package com.ndmrzzzv.countriesinfo.fragment.detail

import androidx.lifecycle.ViewModel
import com.ndmrzzzv.domain.usecase.SearchCountriesByCodeUseCase

class DetailViewModel(
    private val searchCountriesByCodeUseCase: SearchCountriesByCodeUseCase
) : ViewModel() {
}