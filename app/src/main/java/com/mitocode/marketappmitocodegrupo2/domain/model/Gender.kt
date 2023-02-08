package com.mitocode.marketappmitocodegrupo2.domain.model

data class Gender(
    val code:String,
    val description:String
){
    override fun toString(): String {
        return "$description"
    }
}
