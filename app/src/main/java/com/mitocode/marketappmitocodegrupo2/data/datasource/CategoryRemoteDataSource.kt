package com.mitocode.marketappmitocodegrupo2.data.datasource

import com.mitocode.marketappmitocodegrupo2.data.model.RegisterCategoryRequest
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.data.model.WrappedResponse
import com.mitocode.marketappmitocodegrupo2.domain.model.Category

interface CategoryRemoteDataSource {

    suspend fun getCategories() : Result<List<Category>>
    suspend fun save(request:RegisterCategoryRequest) : Result<WrappedResponse<Nothing>>
}