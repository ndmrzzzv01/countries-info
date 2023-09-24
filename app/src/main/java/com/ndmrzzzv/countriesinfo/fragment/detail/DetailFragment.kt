package com.ndmrzzzv.countriesinfo.fragment.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ndmrzzzv.countriesinfo.databinding.FragmentDetailBinding
import com.ndmrzzzv.countriesinfo.feature.LoadingListener
import com.ndmrzzzv.countriesinfo.fragment.detail.interfaces.OnCodeClickListener
import com.ndmrzzzv.countriesinfo.fragment.detail.views.adapter.CodeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(), OnCodeClickListener {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()
    private val adapter = CodeAdapter(listOf(), this)
    private var loading: LoadingListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loading = context as LoadingListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getInfoAboutCountry(args.code)

        initObservers()
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        loading = null
    }

    override fun clickOnCode(code: String) {
        loading?.showLoading()
        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentSelf(code))
    }

    private fun initRecyclerView() {
        binding.rvCodesCountry.adapter = adapter
        binding.rvCodesCountry.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    @SuppressLint("SetTextI18n")
    fun initObservers() {
        binding.apply {
            viewModel.country.observe(viewLifecycleOwner) { listOfCountry ->
                if (listOfCountry == null) {
                    loading?.hideLoading()
                    showViewsIfNoInternet()
                    return@observe
                }

                if (listOfCountry.isNotEmpty()) {
                    loading?.hideLoading()
                    binding.emptyBack.visibility = View.GONE
                    val country = listOfCountry[0]
                    Glide.with(requireContext()).load(country.image).into(imgOfFlag)
                    Glide.with(requireContext()).load(country.coatOfArms).into(imgOfCoatOfArms)

                    tvCountryNameCommon.text = country.name
                    tvOfficialName.text = country.officialName
                    tvCapital.text = "Capital: ${country.capital ?: " -"}"
                    tvPopulationName.text = "Population: ${country.population}"
                    tvSurfaceName.text = "Area: ${country.surface}"

                    var languages = ""
                    for (pair in country.languages ?: mapOf()) {
                        languages += "(${pair.value}) "
                    }
                    tvLanguageName.text = "Languages: $languages"

                    var timeZone = ""
                    for (item in country.timeZone ?: listOf()) {
                        timeZone += "[${item}] "
                    }
                    tvTimezone.text = "Timezones: $timeZone"

                    tvGoogleMapLink.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(country.googleMapLink))
                        startActivity(intent)
                    }

                    adapter.setList(country.countriesNear)
                }
            }
        }
    }

    private fun showViewsIfNoInternet() {
        binding.apply {
            emptyBack.visibility = View.VISIBLE
            btnRetry.setOnClickListener {
                viewModel.getInfoAboutCountry(args.code)
            }
        }
    }

}