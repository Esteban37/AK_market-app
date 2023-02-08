package com.mitocode.marketappmitocodegrupo2.examples

open class Worker constructor(
    val names:String,
    val age:Int) {

    open fun work(){
        println("El trabajador esta laborando")
    }
}