package com.ndmrzzzv.countriesinfo.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.ndmrzzzv.countriesinfo.R
import com.ndmrzzzv.countriesinfo.screens.main.state.CountriesState
import com.ndmrzzzv.domain.model.Country

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesScreen(
    state: CountriesState,
    onItemClick: (code: String) -> Unit = {},
    searchEvent: (searchString: String) -> Unit = {}
) {
    val textValue = remember { mutableStateOf("") }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (btnRefs, textFieldRefs, lazyColumnRefs, loading, error) = createRefs()
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.sort),
            contentDescription = "Sort by",
            Modifier
                .padding(16.dp)
                .width(40.dp)
                .height(40.dp)
                .constrainAs(btnRefs) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                })

        TextField(
            value = textValue.value,
            label = { Text(text = "Type here to find country") },
            onValueChange = {
                textValue.value = it
                searchEvent(it)
            },
            modifier = Modifier
                .padding(end = 16.dp)
                .constrainAs(textFieldRefs) {
                    start.linkTo(btnRefs.end)
                    top.linkTo(btnRefs.top)
                    bottom.linkTo(btnRefs.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
            modifier = Modifier.constrainAs(lazyColumnRefs) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                top.linkTo(btnRefs.bottom)

                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }
        ) {
            items(state.countries) { country ->
                CountryItem(country)
            }
        }

        if (state.isLoading) CircularProgressIndicator(
            Modifier
                .semantics {
                    this.contentDescription = "Progress Indicator"
                }
                .constrainAs(loading) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        if (state.error != null) Text(state.error)
    }

}

@Composable
fun CountryItem(
    country: Country,
//    onItemClick: (code: String) -> Unit = {}
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
    ) {
        val (imgCountry, tvCountryName, tvCapital, tvPopulation, tvSurface) = createRefs()
        AsyncImage(
            model = country.image,
            contentDescription = "Image of Country",
            Modifier
                .width(120.dp)
                .height(120.dp)
                .constrainAs(imgCountry) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .padding(8.dp))

        Text(text = country.name ?: "", fontSize = 16.sp, modifier = Modifier
            .constrainAs(tvCountryName) {
                top.linkTo(imgCountry.top)
                start.linkTo(imgCountry.end)
                end.linkTo(parent.end)

                width = Dimension.fillToConstraints
            }
            .padding(start = 8.dp, end = 8.dp, top = 8.dp))

        Text(text = country.capital.toString(), fontSize = 14.sp, modifier = Modifier
            .constrainAs(tvCapital) {
                top.linkTo(tvCountryName.bottom)
                start.linkTo(tvCountryName.start)
                end.linkTo(tvCountryName.end)
                bottom.linkTo(tvPopulation.top)

                width = Dimension.fillToConstraints
            }
            .padding(start = 8.dp, end = 8.dp))

        Text(
            text = "Population: ${country.population}",
            fontSize = 14.sp,
            modifier = Modifier
                .constrainAs(tvPopulation) {
                    start.linkTo(tvCapital.start)
                    end.linkTo(tvCapital.end)
                    top.linkTo(tvCapital.bottom)
                    bottom.linkTo(tvSurface.top)

                    width = Dimension.fillToConstraints
                }
                .padding(start = 8.dp, end = 8.dp)
        )

        Text(text = "Surface: ${country.surface}", fontSize = 14.sp, modifier = Modifier
            .constrainAs(tvSurface) {
                start.linkTo(tvPopulation.start)
                end.linkTo(tvPopulation.end)
                top.linkTo(tvPopulation.bottom)
                bottom.linkTo(parent.bottom)

                width = Dimension.fillToConstraints
            }
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp))

    }
}