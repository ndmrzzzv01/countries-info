package com.ndmrzzzv.countriesinfo.di

import com.ndmrzzzv.countriesinfo.utils.InternetChecker
import com.ndmrzzzv.countriesinfo.ui.screens.detail.DetailViewModel
import com.ndmrzzzv.countriesinfo.ui.screens.main.MainListViewModel
import com.ndmrzzzv.domain.usecase.GetAllCountriesUseCase
import com.ndmrzzzv.domain.usecase.SearchCountriesByCodeUseCase
import com.ndmrzzzv.domain.usecase.SortAndFilterCountriesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

val appModule = module {

    single { GetAllCountriesUseCase(get()) }

    single { SearchCountriesByCodeUseCase(get()) }

    single { SortAndFilterCountriesUseCase() }

    single { InternetChecker(androidContext()) }

    single<CoroutineDispatcher> { Dispatchers.Main }

    viewModel { MainListViewModel(get(), get(), get(), get()) }

    viewModel { DetailViewModel(get(), get(), get(), get()) }

}