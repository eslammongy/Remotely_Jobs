package com.eslammongy.remotelyjobs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.eslammongy.remotelyjobs.databinding.ActivityHomeBinding
import com.eslammongy.remotelyjobs.repository.RemoteJobsRepo
import com.eslammongy.remotelyjobs.viewModel.RemoteViewModel
import com.eslammongy.remotelyjobs.viewModel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.ibrahimsn.lib.SmoothBottomBar

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    private lateinit var navController: NavController
    lateinit var mainViewModel: RemoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()
         setSupportActionBar(binding.appBar)
         setupActionBarWithNavController(navController)
         setupSmoothBottomMenu()
        setUpViewModel()

    }

    private fun setUpViewModel (){

        val remoteJobsRepository = RemoteJobsRepo()
        val viewModelFactory = ViewModelFactory(application , remoteJobsRepository)
        mainViewModel = ViewModelProvider(this , viewModelFactory).get(RemoteViewModel::class.java)
    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, binding.root)
        popupMenu.inflate(R.menu.bottom_navigation_menu)
        val menu = popupMenu.menu
        binding.bottomBarNav.setupWithNavController(menu, navController)

    }

override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}