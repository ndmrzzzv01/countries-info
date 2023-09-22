package com.ndmrzzzv.countriesinfo.module

import com.ndmrzzzv.countriesinfo.fragment.detail.DetailViewModel
import com.ndmrzzzv.countriesinfo.fragment.main.MainListViewModel
import com.ndmrzzzv.domain.usecase.GetAllCountriesUseCase
import com.ndmrzzzv.domain.usecase.SearchCountriesByCodeUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { GetAllCountriesUseCase(get()) }

    single { SearchCountriesByCodeUseCase(get()) }

    viewModel { MainListViewModel(get()) }

    viewModel { DetailViewModel(get()) }

}