package com.ndmrzzzv.countriesinfo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.ndmrzzzv.countriesinfo.ui.theme.CountriesInfoAppTheme

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

    }

}