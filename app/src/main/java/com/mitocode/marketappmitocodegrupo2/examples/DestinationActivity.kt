package com.mitocode.marketappmitocodegrupo2.examples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mitocode.marketappmitocodegrupo2.R

class DestinationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination)

        //1. Recibir el Bundle
        val bundle = intent.extras

        //Scope functions - let
        //2. Recuperar los valores
        // ! =
        //if(bundle != null) {
        bundle?.let{ bundleNotNull ->

            val names = bundleNotNull.getString("key_names", "") ?: "Desconocido"
            val document = bundleNotNull.getString("key_document", "") ?: "Desconocido"
            val typeDocument = bundleNotNull.getString("key_type_document", "") ?: "Desconocido"

            println(names)
            println(document)
            println(typeDocument)

            println("Hola $names .Tus datos son: $typeDocument - $document")
        }

        //val names2 : String? = "Juan Jose"
        //getNames(names2!!)

        //CLASES Y OBJETOS - Instanciar una clase
        val juanjose = Person("Juan Jose","Ledesma")
        println(juanjose.names)
        println(juanjose.lastName)

        val david = Person("David","Peralta")
        println(david.lastName)

        val esteban = Person("Esteban")
        esteban.lastName = "Serrano"

        val diego = Person()

        //COPY
        val juanjose2 = juanjose.copy(lastName = "Ledesma Zevallos")
        juanjose2.names
        juanjose2.lastName

        val (name,lastName) = Person("Rodrigo","Ledesma")
        println(name)
        println(lastName)

        Person.saludo
        Person.saludar()

        val developer = Developer("Juan Jose",33,4)
        developer.languages
        developer.work()

        val designer = Designer("Diego",30,2)
        designer.names
        designer.work()




    }


    /*fun getNames(name:String){

    }*/

}