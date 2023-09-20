package com.ndmrzzzv.countriesinfo.module

import com.ndmrzzzv.countriesinfo.fragment.main.MainListViewModel
import com.ndmrzzzv.domain.usecase.GetAllCountriesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { GetAllCountriesUseCase(get()) }

    viewModel { MainListViewModel(get()) }

}