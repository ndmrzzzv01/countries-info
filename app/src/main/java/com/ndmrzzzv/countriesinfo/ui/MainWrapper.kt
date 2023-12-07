package com.ndmrzzzv.countriesinfo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ndmrzzzv.countriesinfo.activity.ActionsBuilder
import com.ndmrzzzv.countriesinfo.ui.screens.detail.CountryDetailScreen
import com.ndmrzzzv.countriesinfo.ui.screens.detail.DetailViewModel
import com.ndmrzzzv.countriesinfo.ui.screens.main.CountriesScreen
import com.ndmrzzzv.countriesinfo.ui.screens.main.MainListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CountriesApp() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = "countries"
    ) {
        composable(route = "countries") {
            val viewModel = koinViewModel<MainListViewModel>()
            val actions = ActionsBuilder.getActions(navController, viewModel)
            CountriesScreen(
                state = viewModel.sortedCountries.collectAsState().value,
                actions = actions,
                savedString = viewModel.searchText.value ?: ""
            )
        }
        composable(
            route = "countries/{country_code}",
            arguments = listOf(navArgument("country_code") {
                type = NavType.StringType
            })
        ) {
            val viewModel = koinViewModel<DetailViewModel>()
            val actions = ActionsBuilder.getActions(navController, viewModel, LocalContext.current)
            CountryDetailScreen(countryState = viewModel.country.value, actions = actions)
        }
    }
}