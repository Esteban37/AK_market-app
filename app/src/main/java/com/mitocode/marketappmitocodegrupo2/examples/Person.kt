package com.mitocode.marketappmitocodegrupo2.examples

//Plantilla
data class Person constructor(
    val names:String="",
    var lastName:String="") {

    companion object{
        val saludo:String = "Hello"

        fun saludar(){

        }
    }


    constructor(names:String) : this(names,"")
    //constructor() : this("","")

    //Atributos
    //var names:String = ""
    //var lastName:String = ""

    /*init{
        this.names = names
        this.lastName = lastName
    }*/

    /*constructor(names:String,lastName:String){
        this.names = names
        this.lastName = lastName
    }*/

}

