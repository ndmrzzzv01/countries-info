package com.ndmrzzzv.countriesinfo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.ndmrzzzv.countriesinfo.R
import com.ndmrzzzv.countriesinfo.databinding.ActivityMainBinding
import com.ndmrzzzv.countriesinfo.feature.LoadingListener

class MainActivity : AppCompatActivity(), LoadingListener {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
    private var navHostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment?.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(binding.navHostFragment.id)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.backForBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.backForBar.visibility = View.GONE
    }
}