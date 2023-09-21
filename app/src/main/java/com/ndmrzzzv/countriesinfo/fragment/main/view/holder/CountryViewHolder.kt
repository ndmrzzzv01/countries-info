package com.ndmrzzzv.countriesinfo.fragment.main.view.holder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.ndmrzzzv.countriesinfo.databinding.ItemCountryBinding
import com.ndmrzzzv.domain.model.Country

class CountryViewHolder(private val binding: ItemCountryBinding) : ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(country: Country) {
        var capital = ""
        if (country.capital != null) {
            for(item in country.capital ?: listOf()) {
                capital += "(${item}) "
            }
        }

        binding.apply {
            tvCountryName.text = country.name
            tvCapitalName.text = capital
            tvPopulationName.text = "Population: ${country.population}"
            tvSurfaceName.text = "Surface: ${country.surface}"

            Glide
                .with(imgOfFlag.context)
                .load(country.image)
                .into(imgOfFlag)
        }
    }

}