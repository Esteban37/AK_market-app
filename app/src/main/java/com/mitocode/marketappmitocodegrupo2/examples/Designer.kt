package com.mitocode.marketappmitocodegrupo2.examples

class Designer constructor(
    names:String,
    age:Int,
    val tools:Int) : Worker(names,age) {

    override fun work() {
        println("El designer esta laborando")
    }
}