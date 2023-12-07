package com.ndmrzzzv.countriesinfo.activity

import android.content.Context
import androidx.navigation.NavHostController
import com.ndmrzzzv.countriesinfo.ui.screens.detail.CountryDetailScreenAction
import com.ndmrzzzv.countriesinfo.ui.screens.detail.DetailViewModel
import com.ndmrzzzv.countriesinfo.ui.screens.main.CountriesScreenAction
import com.ndmrzzzv.countriesinfo.ui.screens.main.MainListViewModel

class ActionsBuilder {

    companion object {

        fun getActions(navController: NavHostController, viewModel: MainListViewModel): CountriesScreenAction {
            return CountriesScreenAction(
                onItemClick = { code -> navController.navigate("countries/$code") },
                searchEvent = { searchString -> viewModel.setSearchText(searchString) },
                sortEvent = { type -> viewModel.setTypeSort(type) },
                getAllCountriesEvent = { viewModel.getAllCountries() }
            )
        }

        fun getActions(
            navController: NavHostController,
            viewModel: DetailViewModel,
            context: Context
        ): CountryDetailScreenAction {
            return CountryDetailScreenAction(
                openGoogleMap = { url -> viewModel.openGoogleMapLink(context, url) },
                onCodeClick = { code -> navController.navigate("countries/$code") },
                loadCountryAgainEvent = { viewModel.loadInfoAboutCountry() }
            )
        }

    }

}