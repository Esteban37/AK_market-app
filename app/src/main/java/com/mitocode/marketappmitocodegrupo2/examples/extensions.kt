package com.mitocode.marketappmitocodegrupo2.examples

import android.content.Context
import android.view.View
import android.widget.Toast

//fun Context.toast(message:String) = Toast.makeText(this, message , Toast.LENGTH_LONG).show()

// :()->Unit
infix fun View.click(click:()->Unit){
    setOnClickListener { click }
}