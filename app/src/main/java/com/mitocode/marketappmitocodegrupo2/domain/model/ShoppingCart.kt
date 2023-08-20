package com.mitocode.marketappmitocodegrupo2.domain.model

import java.io.Serializable

class ShoppingCart (
    val id:Int,
    val categoryCode:String,
    val productCode:String,
    val productDescription:String,
    //val userCode:String,
    var amount: Int,
    val price: Double,
    val image: String,
    var total: Double
)

class ShoppingCartList(
    val shoppingCartList: List<ShoppingCart>
): Serializable
