package com.ndmrzzzv.countriesinfo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ndmrzzzv.countriesinfo.screens.detail.CountryDetailScreen
import com.ndmrzzzv.countriesinfo.screens.detail.DetailViewModel
import com.ndmrzzzv.countriesinfo.screens.main.CountriesScreen
import com.ndmrzzzv.countriesinfo.screens.main.MainListViewModel
import com.ndmrzzzv.countriesinfo.ui.theme.CountriesInfoAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountriesInfoAppTheme {
                CountriesApp()
            }
        }

    }

    @Composable
    private fun CountriesApp() {
        val navController = rememberNavController()
        NavHost(
            navController,
            startDestination = "countries"
        ) {
            val actions = CountriesScreensNavigation()
            actions.initializeNavController(navController)
            composable(route = "countries") {
                val viewModel = koinViewModel<MainListViewModel>()
                val mainActions = actions.getActions(viewModel)
                CountriesScreen(
                    state = viewModel.sortedCountries.value,
                    onItemClick = mainActions.onItemClick,
                    searchEvent = mainActions.searchEvent,
                    sortEvent = mainActions.sortEvent,
                    getAllCountriesEvent = mainActions.getAllCountriesEvent,
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
                val detailsActions = actions.getActions(viewModel, this@MainActivity)
                CountryDetailScreen(
                    countryState = viewModel.country.value,
                    openGoogleMap = detailsActions.openGoogleMap,
                    onCodeClick = detailsActions.onCodeClick,
                    loadCountryAgainEvent = detailsActions.loadCountryAgainEvent
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CountriesInfoAppTheme {}
}