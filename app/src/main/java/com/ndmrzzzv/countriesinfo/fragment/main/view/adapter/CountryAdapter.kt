package com.ndmrzzzv.countriesinfo.fragment.main.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ndmrzzzv.countriesinfo.databinding.ItemCountryBinding
import com.ndmrzzzv.countriesinfo.fragment.main.interfaces.GoToDetailedItemListener
import com.ndmrzzzv.countriesinfo.fragment.main.view.holder.CountryViewHolder
import com.ndmrzzzv.domain.model.Country

class CountryAdapter(
    private var countries: List<Country>,
    private val goToDetailedItemListener: GoToDetailedItemListener
) : Adapter<CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country)

        holder.itemView.setOnClickListener {
            goToDetailedItemListener.navigateToOneItem(country.code)
        }
    }

    override fun getItemCount(): Int = countries.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listOfCountries: List<Country>) {
        countries = listOfCountries
        notifyDataSetChanged()
    }
}