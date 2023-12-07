package com.ndmrzzzv.countriesinfo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ndmrzzzv.countriesinfo.ui.CountriesApp
import com.ndmrzzzv.countriesinfo.ui.screens.detail.CountryDetailScreen
import com.ndmrzzzv.countriesinfo.ui.screens.detail.DetailViewModel
import com.ndmrzzzv.countriesinfo.ui.screens.main.CountriesScreen
import com.ndmrzzzv.countriesinfo.ui.screens.main.MainListViewModel
import com.ndmrzzzv.countriesinfo.ui.theme.CountriesInfoAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainWrapper()
        }

    }

    @Composable
    fun MainWrapper() {
        CountriesInfoAppTheme {
            CountriesApp()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CountriesInfoAppTheme {}
}