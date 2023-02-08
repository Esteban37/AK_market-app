package com.mitocode.marketappmitocodegrupo2.presentation.shop

data class CategoryCreateState(
    val isLoading : Boolean = false,
    val error:String? = null,
    val success:String? = null
)
