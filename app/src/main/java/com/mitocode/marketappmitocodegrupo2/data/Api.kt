package com.mitocode.marketappmitocodegrupo2.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

/*object Api {

    //URL o Endpoint = http://18.234.189.188:3000/api/usuarios/login
    //Base URL = http://18.234.189.188:3000/
    //Methods = api/usuarios/login

    private val builder : Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl("http://18.234.189.188:3000/")
            .addConverterFactory(GsonConverterFactory.create())


    interface RemoteService{

        //@POST("api/usuarios/login")
        //fun signIn(@Body request : LoginRequest) : Call<WrappedResponse<UserRemote>>

        @POST("api/usuarios/login")
        suspend fun signIn(@Body request : LoginRequest) : Response<WrappedResponse<UserRemote>>

    }

    fun build() : RemoteService{
        return builder.build().create(RemoteService::class.java)
    }

}*/