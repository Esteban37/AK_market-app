package com.mitocode.marketappmitocodegrupo2.data.repositories

import com.mitocode.marketappmitocodegrupo2.data.datasource.ProductRemoteDataSource
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productRemoteDataSource: ProductRemoteDataSource) {

    suspend fun getProducts(uuid:String) : Flow<Result<List<Product>>> {
        return flow {
            emit(productRemoteDataSource.getProducts(uuid))
        }
    }

}