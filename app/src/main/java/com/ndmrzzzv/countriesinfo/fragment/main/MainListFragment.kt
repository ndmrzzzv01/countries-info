package com.ndmrzzzv.countriesinfo.fragment.main

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ndmrzzzv.countriesinfo.R
import com.ndmrzzzv.countriesinfo.databinding.FragmentMainListBinding
import com.ndmrzzzv.countriesinfo.feature.LoadingListener
import com.ndmrzzzv.countriesinfo.fragment.main.data.MapForSorting
import com.ndmrzzzv.countriesinfo.fragment.main.interfaces.GoToDetailedItemListener
import com.ndmrzzzv.countriesinfo.fragment.main.view.adapter.CountryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainListFragment : Fragment(), GoToDetailedItemListener {

    private var _binding: FragmentMainListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<MainListViewModel>()
    private var loading: LoadingListener? = null
    private val adapter = CountryAdapter(listOf(), this)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loading = context as LoadingListener
    }

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
        viewModel.getAllCountries()
        initListeners()
        initRecyclerView()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        loading = null
    }

    override fun navigateToOneItem(code: String?) {
        loading?.showLoading()
        findNavController().navigate(
            MainListFragmentDirections.actionMainListFragmentToDetailFragment(
                code ?: ""
            )
        )
    }

    private fun initListeners() {
        binding.btnFilter.setOnClickListener {
            showMenu(it, R.menu.sort_menu)
        }

        binding.etSearch.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                viewModel.setSearchText(binding.etSearch.text.toString())
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
            loading?.hideLoading()
            if (it == null) {
                showViewsIfNoInternet()
                return@observe
            }

            adapter.setList(it)
            binding.emptyBack.visibility = View.GONE
        }
    }

    private fun showViewsIfNoInternet() {
        binding.apply {
            emptyBack.visibility = View.VISIBLE
            btnRetry.setOnClickListener {
                viewModel.getAllCountries()
            }
        }
    }

    private fun showMenu(view: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            val map = MapForSorting.get()
            for (pair in map) {
                if (item.itemId == pair.key) {
                    viewModel.setSortType(pair.value)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popup.show()
    }

}