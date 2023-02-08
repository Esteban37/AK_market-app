package com.mitocode.marketappmitocodegrupo2.data.datasource

import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.domain.model.Product

interface ProductRemoteDataSource {

    suspend fun getProducts(uuid:String) : Result<List<Product>>

}