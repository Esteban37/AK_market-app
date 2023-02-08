package com.mitocode.marketappmitocodegrupo2.presentation.products

import com.mitocode.marketappmitocodegrupo2.domain.model.Product

data class ProductState(
   val isLoading : Boolean = false,
   val error:String?=null,
   val products:List<Product>? = null
)