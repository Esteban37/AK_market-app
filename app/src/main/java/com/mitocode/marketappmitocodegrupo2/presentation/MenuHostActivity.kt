package com.mitocode.marketappmitocodegrupo2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.mitocode.marketappmitocodegrupo2.R
import com.mitocode.marketappmitocodegrupo2.databinding.ActivityMenuHostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuHostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMenuHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_menu_host)

        val navController = Navigation.findNavController(this,R.id.menuHostFragment)
        NavigationUI.setupWithNavController(binding.navigationView,navController)

        binding.imgMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }


    }
}