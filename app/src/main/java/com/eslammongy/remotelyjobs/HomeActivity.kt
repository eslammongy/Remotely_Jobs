package com.eslammongy.remotelyjobs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.eslammongy.remotelyjobs.databinding.ActivityHomeBinding
import com.eslammongy.remotelyjobs.db.DataBaseBuilder
import com.eslammongy.remotelyjobs.repository.RemoteJobsRepo
import com.eslammongy.remotelyjobs.viewModel.RemoteViewModel
import com.eslammongy.remotelyjobs.viewModel.ViewModelFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    lateinit var mainViewModel: RemoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()
        val appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.softEngineerFragment,
                    R.id.designFragment,
                    R.id.marketingFragment,
                    R.id.customerServicesFragment,
                    R.id.savedJobsFragment
                )
            )
        setSupportActionBar(binding.appBar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomBarNav.setupWithNavController(navController)

        binding.btnSearchForJobs.setOnClickListener {
            navHostFragment.findNavController().navigate(R.id.globalActionToSearchFragment)
        }
        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.searchFragment -> hideBottomViewPager()
                    else -> showBottomViewPager()
                }
            }
        setUpViewModel()

    }

    private fun hideBottomViewPager() {
        binding.topBar.isVisible = false
        binding.bottomBarNav.isVisible = false
    }

    private fun showBottomViewPager() {
        binding.topBar.isVisible = true
        binding.bottomBarNav.isVisible = true

    }

    private fun setUpViewModel() {

        val remoteJobsRepository = RemoteJobsRepo(DataBaseBuilder(this))
        val viewModelFactory = ViewModelFactory(application, remoteJobsRepository)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(RemoteViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}