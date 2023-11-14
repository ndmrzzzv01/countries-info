package com.ndmrzzzv.countriesinfo.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.ndmrzzzv.countriesinfo.screens.main.state.CountriesState
import com.ndmrzzzv.domain.model.Country

@Composable
fun CountriesScreen(
    state: CountriesState,
    onItemClick: (code: String) -> Unit = {}
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn(contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)) {
            items(state.countries) { country ->
                CountryItem(
                    country, onItemClick = { code -> onItemClick(code) }
                )
            }
        }

        if (state.isLoading) CircularProgressIndicator(
            Modifier.semantics {
                this.contentDescription = "Progress Indicator"
            }
        )

        if (state.error != null) Text(state.error)

    }

}

@Composable
fun CountryItem(
    country: Country,
    onItemClick: (code: String) -> Unit = {}
) {

}