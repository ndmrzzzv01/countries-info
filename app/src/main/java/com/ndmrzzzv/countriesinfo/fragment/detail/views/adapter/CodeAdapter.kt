package com.ndmrzzzv.countriesinfo.fragment.detail.views.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ndmrzzzv.countriesinfo.databinding.ItemCodeBinding
import com.ndmrzzzv.countriesinfo.fragment.detail.interfaces.OnCodeClickListener
import com.ndmrzzzv.countriesinfo.fragment.detail.views.holder.CodeViewHolder

class CodeAdapter(
    private var codes: List<String>?,
    private val onCodeClickListener: OnCodeClickListener
) : Adapter<CodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeViewHolder {
        val binding = ItemCodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CodeViewHolder, position: Int) {
        val code = codes?.get(position)
        holder.bind(code)

        holder.itemView.setOnClickListener {
            onCodeClickListener.clickOnCode(code ?: "")
        }
    }

    override fun getItemCount(): Int = codes?.size ?: 0

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listOfCodes: List<String>?) {
        codes = listOfCodes
        notifyDataSetChanged()
    }
}