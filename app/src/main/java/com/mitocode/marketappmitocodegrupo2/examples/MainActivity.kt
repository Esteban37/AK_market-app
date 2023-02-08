package com.mitocode.marketappmitocodegrupo2.examples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.mitocode.marketappmitocodegrupo2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1. Inyectar mis vistas, findviewbyid - kotlin extension - viewbinding
        val edtNames : EditText = findViewById(R.id.edtNames)
        val edtDocument : EditText = findViewById(R.id.edtDocument)

        /*btnSend click{

        }*/

        //2. Evento Onclick
        btnSend.setOnClickListener {

            //3. Obteniendo los datos del formulario
            val names = edtNames.text.toString()
            val document = edtDocument.text.toString()
            val typeDocument =  if(rbDni.isChecked) "DNI" else "Carnet"

            //4. Validar campos en blanco
            if(names.isEmpty()){
                //Toast.makeText(this,getString(R.string.activity_main_message_names),Toast.LENGTH_LONG).show()
                //toast(getString(R.string.activity_main_message_names))
                this.toast(getString(R.string.activity_main_message_names))
                return@setOnClickListener
            }

            if(document.isEmpty()){
                //Toast.makeText(this,getString(R.string.activity_main_message_document),Toast.LENGTH_LONG).show()
                toast(getString(R.string.activity_main_message_document))
                return@setOnClickListener
            }

            //if(!rbDni.isChecked && !rbCarnet.isChecked){ }

            //SCOPE FUNCTIONS: apply
            val bundle = Bundle().apply {
                putString("key_names",names)
                putString("key_document",document)
                putString("key_type_document",typeDocument)
            }

            //5. Navegar a la siguiente pantalla
            val intent = Intent(this, DestinationActivity::class.java).apply {
                intent.putExtras(bundle)
            }
            startActivity(intent)

        }

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()


    }

    override fun onResume() {
        super.onResume()
    }


    //Functions
    fun add(number1:Int,number2:Int) : Int{
        return number1 + number2
    }
    fun add2(number1:Int,number2:Int) = number1 + number2
    fun hello(name:String) : Unit{
        println("Hola $name")
    }
    fun hello2(name:String) = println("Hola $name")
    fun hello2(name:String,name2:String) = println("Hola $name")

    //Extensions Functions
    fun Int.plus10() = this + 10
    fun String.helloExtension() = println("Hola $this")

    //fun toast(message:String) = Toast.makeText(this, message ,Toast.LENGTH_LONG).show()

    //Infix functions
    infix fun Int.mas(number2:Int) = this + number2

}

/*
//Variables
        //Mutables
        var names = "Juan Jose"
        var age = 33
        var price = 20.0
        var condition = true
        var letter = 'J'

        names = "Juan Jose Ledesma"

        //Immutables
        val pi = 3.1416
 */