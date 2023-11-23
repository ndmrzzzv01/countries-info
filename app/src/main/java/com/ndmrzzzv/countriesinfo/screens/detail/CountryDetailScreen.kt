package com.ndmrzzzv.countriesinfo.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.ndmrzzzv.countriesinfo.R
import com.ndmrzzzv.countriesinfo.screens.detail.state.CountryDetailState

@Composable
fun CountryDetailScreen(
    countryState: CountryDetailState,
    openGoogleMap: (url: String?) -> Unit = {},
    onCodeClick: (code: String?) -> Unit = {},
    loadCountryAgainEvent: () -> Unit = {}
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (mainImage, secondImage, countryName, officialName, divider,
            capital, population, surface, language, divider2,
            timezone, imgGoogle, tvGoogleMap, divider3, neighboringCountriesTitle,
            listOfNeighboringCountries) = createRefs()
        val (loading, tvError, btnRetry) = createRefs()

        when (countryState) {
            is CountryDetailState.LoadedData -> {
                val country = countryState.country
                if (country != null) {
                    AsyncImage(
                        model = country.image,
                        contentDescription = "Main image",
                        Modifier
                            .constrainAs(mainImage) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)

                                width = Dimension.matchParent
                            }
                            .defaultMinSize(minHeight = 100.dp))

                    AsyncImage(
                        model = country.coatOfArms,
                        contentDescription = "Second image",
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .padding(top = 8.dp, bottom = 8.dp)
                            .constrainAs(secondImage) {
                                top.linkTo(mainImage.bottom)
                                end.linkTo(parent.end)
                            })

                    Text(text = "Name: ${country.name}", fontSize = 18.sp, modifier = Modifier
                        .padding(16.dp, 16.dp, 8.dp)
                        .constrainAs(countryName) {
                            top.linkTo(mainImage.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(secondImage.start)

                            width = Dimension.fillToConstraints
                        })

                    Text(text = "Official name: ${country.officialName}",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(16.dp, 8.dp, 8.dp)
                            .constrainAs(officialName) {
                                top.linkTo(countryName.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(secondImage.start)
                                bottom.linkTo(divider.top)

                                height = Dimension.wrapContent
                                width = Dimension.fillToConstraints
                            })

                    Divider(thickness = 1.dp, color = Color.Gray, modifier = Modifier
                        .padding(8.dp, 8.dp, 8.dp)
                        .constrainAs(divider) {
                            top.linkTo(officialName.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })

                    val capitalTitle =
                        if (country.capital.isNullOrEmpty()) " - " else country.capital.toString()
                    Text(text = "Capital: $capitalTitle", fontSize = 16.sp, modifier = Modifier
                        .padding(16.dp, 8.dp, 8.dp)
                        .constrainAs(capital) {
                            top.linkTo(divider.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)

                            width = Dimension.fillToConstraints
                        })

                    Text(text = "Population: ${country.population}",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(16.dp, 8.dp, 8.dp)
                            .constrainAs(population) {
                                top.linkTo(capital.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)

                                width = Dimension.fillToConstraints
                            })

                    Text(text = "Surface: ${country.surface}",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(16.dp, 8.dp, 8.dp)
                            .constrainAs(surface) {
                                top.linkTo(population.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)

                                width = Dimension.fillToConstraints
                            })

                    val languageList = country.languages?.values
                    val languageTitle =
                        if (languageList.isNullOrEmpty()) " - " else languageList.toString()
                    Text(text = "Language: $languageTitle",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(16.dp, 8.dp, 8.dp)
                            .constrainAs(language) {
                                top.linkTo(surface.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)

                                width = Dimension.fillToConstraints
                            })

                    Divider(thickness = 1.dp, color = Color.Gray, modifier = Modifier
                        .padding(8.dp, 16.dp, 8.dp)
                        .constrainAs(divider2) {
                            top.linkTo(language.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })

                    val timezoneTitle =
                        if (country.timeZone.isNullOrEmpty()) " - " else country.timeZone.toString()
                    Text(text = "Timezone: $timezoneTitle",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(16.dp, 8.dp, 8.dp)
                            .constrainAs(timezone) {
                                top.linkTo(divider2.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)

                                width = Dimension.fillToConstraints
                            })

                    Image(
                        bitmap = ImageBitmap.imageResource(id = R.drawable.map),
                        contentDescription = "Image of google map",
                        modifier = Modifier
                            .padding(8.dp, 8.dp, 8.dp)
                            .width(60.dp)
                            .height(60.dp)
                            .constrainAs(imgGoogle) {
                                top.linkTo(timezone.bottom)
                                start.linkTo(parent.start)
                            })

                    Text(
                        text = "Click on this link to open the desired country on Google Map",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .constrainAs(tvGoogleMap) {
                                top.linkTo(imgGoogle.top)
                                start.linkTo(imgGoogle.end)
                                bottom.linkTo(imgGoogle.bottom)
                                end.linkTo(parent.end)

                                width = Dimension.fillToConstraints
                            }
                            .clickable {
                                openGoogleMap(country.googleMapLink)
                            },
                        textAlign = TextAlign.Center
                    )

                    Divider(thickness = 1.dp, color = Color.Gray, modifier = Modifier
                        .padding(8.dp, 16.dp, 8.dp)
                        .constrainAs(divider3) {
                            top.linkTo(tvGoogleMap.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })

                    val countriesNear = country.countriesNear
                    if (!countriesNear.isNullOrEmpty()) {
                        Text(
                            text = "Neighboring countries", fontSize = 16.sp, modifier = Modifier
                                .padding(16.dp, 8.dp, 8.dp)
                                .constrainAs(neighboringCountriesTitle) {
                                    top.linkTo(divider3.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)

                                    width = Dimension.fillToConstraints
                                }, textAlign = TextAlign.Center
                        )

                        LazyRow(modifier = Modifier
                            .padding(8.dp)
                            .constrainAs(listOfNeighboringCountries) {
                                start.linkTo(parent.start)
                                top.linkTo(neighboringCountriesTitle.bottom)
                                end.linkTo(parent.end)

                                width = Dimension.matchParent
                            }) {


                            items(countriesNear) {
                                ItemCode(code = it) { code ->
                                    onCodeClick(code)
                                }
                            }

                        }
                    }
                }
            }

            is CountryDetailState.LoadingFailed -> {
                Text(
                    text = countryState.message,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.constrainAs(tvError) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)

                        width = Dimension.matchParent
                    })
                Button(
                    onClick = { loadCountryAgainEvent() },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_200)),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .constrainAs(btnRetry) {
                            top.linkTo(tvError.bottom)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        }
                ) {
                    Text(text = "Retry")
                }
            }

            is CountryDetailState.Loading -> {
                CircularProgressIndicator(
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
            }
        }

    }
}


@Composable
fun ItemCode(code: String, onCodeClick: (code: String?) -> Unit = {}) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            .clickable {
                onCodeClick(code)
            }
            .padding(8.dp), contentAlignment = Alignment.Center
    ) {
        Text(text = code)
    }
}