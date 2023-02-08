package com.mitocode.marketappmitocodegrupo2.data.model

data class WrappedResponse<T>(
    val success:Boolean,
    val message:String,
    val data: T? = null,
    val token : String? = null
)

data class WrappedListResponse<T>(
    val success:Boolean,
    val message:String,
    val data: List<T>? = null
)

