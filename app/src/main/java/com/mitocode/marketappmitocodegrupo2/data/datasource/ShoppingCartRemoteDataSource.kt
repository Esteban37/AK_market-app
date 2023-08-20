package com.mitocode.marketappmitocodegrupo2.data.datasource

import com.mitocode.marketappmitocodegrupo2.data.model.RegisterPurchaseRequest
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.data.model.WrappedResponse

interface ShoppingCartRemoteDataSource {

    suspend fun save(request:RegisterPurchaseRequest) : Result<WrappedResponse<Nothing>>

}