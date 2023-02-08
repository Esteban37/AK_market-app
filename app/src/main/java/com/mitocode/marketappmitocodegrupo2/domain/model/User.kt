package com.mitocode.marketappmitocodegrupo2.domain.model


data class User(
    val uuid:String,
    val names:String,
    val lastName:String,
    val email:String,
    val phone:String,
    val gender:String,
    val numberDoc:String,
    val type:String,
)
