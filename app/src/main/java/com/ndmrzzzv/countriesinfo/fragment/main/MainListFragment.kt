package com.ndmrzzzv.countriesinfo.fragment.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ndmrzzzv.countriesinfo.R
import com.ndmrzzzv.countriesinfo.databinding.FragmentMainListBinding
import com.ndmrzzzv.countriesinfo.fragment.main.data.MapForSorting
import com.ndmrzzzv.countriesinfo.fragment.main.view.adapter.CountryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainListFragment : Fragment() {

    private var _binding: FragmentMainListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<MainListViewModel>()
    private val adapter = CountryAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRecyclerView()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        binding.btnFilter.setOnClickListener {
            showMenu(it, R.menu.sort_menu)
        }

        binding.etSearch.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                viewModel.searchByName(binding.etSearch.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun initRecyclerView() {
        binding.rvCountries.adapter = adapter
        binding.rvCountries.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initObservers() {
        viewModel.countries.observe(viewLifecycleOwner) {
            adapter.setList(it ?: listOf())
        }
    }

    private fun showMenu(view: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            val map = MapForSorting.get()
            for (pair in map) {
                if (item.itemId == pair.key) {
                    viewModel.sort(pair.value)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popup.show()
    }

}