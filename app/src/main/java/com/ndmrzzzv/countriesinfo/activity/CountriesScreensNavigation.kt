package com.ndmrzzzv.countriesinfo.activity

import android.content.Context
import androidx.navigation.NavHostController
import com.ndmrzzzv.countriesinfo.screens.detail.DetailViewModel
import com.ndmrzzzv.countriesinfo.screens.detail.state.CountryDetailScreenAction
import com.ndmrzzzv.countriesinfo.screens.main.MainListViewModel
import com.ndmrzzzv.countriesinfo.screens.main.state.CountriesScreenAction

class CountriesScreensNavigation {

    private var navController: NavHostController? = null

    fun initializeNavController(navController: NavHostController){
        this.navController = navController
    }

    fun getActions(viewModel: MainListViewModel): CountriesScreenAction {
        return CountriesScreenAction(
            onItemClick = { code -> navController?.navigate("countries/$code") },
            searchEvent = { searchString -> viewModel.setSearchText(searchString) },
            sortEvent = { type -> viewModel.setTypeSort(type) },
            getAllCountriesEvent = { viewModel.getAllCountries() }
        )
    }

    fun getActions(
        viewModel: DetailViewModel,
        context: Context
    ): CountryDetailScreenAction {
        return CountryDetailScreenAction(
            openGoogleMap = { url -> viewModel.openGoogleMapLink(context, url) },
            onCodeClick = { code -> navController?.navigate("countries/$code") },
            loadCountryAgainEvent = { viewModel.loadInfoAboutCountry() }
        )
    }

}