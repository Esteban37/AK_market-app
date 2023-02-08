package com.mitocode.marketappmitocodegrupo2.data.model

sealed class Result<T>(val data:T?=null, val message:String?=null){

    //SUCCESS - ERROR
    class Success<T>(data:T?) : Result<T>(data)
    class Error<T>(message: String, data:T?=null) : Result<T>(data,message)
}
