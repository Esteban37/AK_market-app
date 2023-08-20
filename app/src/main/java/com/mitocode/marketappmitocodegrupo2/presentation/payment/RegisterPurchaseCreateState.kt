package com.mitocode.marketappmitocodegrupo2.presentation.payment

data class RegisterPurchaseCreateState (

    val isLoading : Boolean = false,
    val error:String? = null,
    val success:String? = null

)