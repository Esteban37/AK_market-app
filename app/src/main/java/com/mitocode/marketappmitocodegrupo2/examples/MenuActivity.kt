package com.mitocode.marketappmitocodegrupo2.examples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mitocode.marketappmitocodegrupo2.R
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    //private val fragment : Fragment? = null
    private lateinit var fragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnForm.setOnClickListener {
            fragment = FormFragment()
            addFragment()
        }

        btnList.setOnClickListener {
            fragment = ListFragment()
            addFragment()
        }

    }

    private fun addFragment(){
        //if(fragment!=null){
        fragment?.let {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView,fragment)
                .commit()
        }
    }

}