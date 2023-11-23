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
import com.ndmrzzzv.countriesinfo.screens.main.state.CountriesState
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
            composable(route = "countries") {
                val viewModel = koinViewModel<MainListViewModel>()
                CountriesScreen(
                    state = viewModel.sortedCountries.value,
                    onItemClick = { code -> navController.navigate("countries/$code") },
                    searchEvent = { searchString -> viewModel.setSearchText(searchString) },
                    sortEvent = { type -> viewModel.setTypeSort(type) },
                    getAllCountriesEvent = { viewModel.getAllCountries() },
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
                CountryDetailScreen(
                    countryState = viewModel.country.value,
                    openGoogleMap = { url ->
                        viewModel.openGoogleMapLink(this@MainActivity, url)
                    },
                    onCodeClick = { code ->
                        navController.navigate("countries/$code")
                    },
                    loadCountryAgainEvent = {
                        viewModel.loadInfoAboutCountry()
                    }
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CountriesInfoAppTheme {
//        CountriesScreen(CountriesState(listOf(), true),
//            {}) {}
    }
}