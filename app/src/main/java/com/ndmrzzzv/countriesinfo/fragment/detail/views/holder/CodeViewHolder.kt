package com.ndmrzzzv.countriesinfo.fragment.detail.views.holder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ndmrzzzv.countriesinfo.databinding.ItemCodeBinding

class CodeViewHolder(private val binding: ItemCodeBinding) : ViewHolder(binding.root) {

    fun bind(code: String?) {
        binding.tvCode.text = code
    }

}