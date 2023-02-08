package com.mitocode.marketappmitocodegrupo2.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

abstract class BaseFragment(@LayoutRes val layoutRes:Int) : Fragment(layoutRes) {

    private fun navigation() : NavController?{
        return view?.let {
            Navigation.findNavController(it)
        }
    }

    protected fun navigateToAction(action:Int){
        navigation()?.navigate(action)
    }

    protected fun navigateToDirections(directions:NavDirections){
        navigation()?.navigate(directions)
    }

}


