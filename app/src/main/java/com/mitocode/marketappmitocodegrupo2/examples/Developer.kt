package com.mitocode.marketappmitocodegrupo2.examples

class Developer constructor(
    names:String,
    age:Int,
    val languages:Int) : Worker(names,age) {

    override fun work() {
        println("El developer esta laborando")
    }
}