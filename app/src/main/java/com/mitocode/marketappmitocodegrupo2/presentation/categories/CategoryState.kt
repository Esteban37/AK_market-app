package com.mitocode.marketappmitocodegrupo2.presentation.categories

import com.mitocode.marketappmitocodegrupo2.domain.model.Category

data class CategoryState(
    val isLoading:Boolean=false,
    val error:String?=null,
    val categories:List<Category>? = null
)
