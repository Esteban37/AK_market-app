package com.mitocode.marketappmitocodegrupo2.domain.model

import java.io.Serializable

data class Product(
    val quantity: Int,
    val feautures: String,
    val code: String,
    val description: String,
    val images: List<String>,
    val price: Double,
    val stock: Int,
    val uuid: String
) : Serializable