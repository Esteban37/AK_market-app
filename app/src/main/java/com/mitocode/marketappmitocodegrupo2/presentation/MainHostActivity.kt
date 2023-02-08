package com.mitocode.marketappmitocodegrupo2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mitocode.marketappmitocodegrupo2.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_host)
    }
}