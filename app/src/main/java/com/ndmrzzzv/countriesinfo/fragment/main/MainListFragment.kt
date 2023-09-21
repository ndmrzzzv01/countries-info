package com.ndmrzzzv.countriesinfo.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ndmrzzzv.countriesinfo.databinding.FragmentMainListBinding
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
        initRecyclerView()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

}