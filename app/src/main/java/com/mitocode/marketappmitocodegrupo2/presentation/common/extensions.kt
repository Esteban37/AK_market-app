package com.mitocode.marketappmitocodegrupo2.presentation.common

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.toast(message:String) =
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()

fun View.gone(){
    visibility = View.GONE
}

fun View.visible(){
    visibility = View.VISIBLE
}