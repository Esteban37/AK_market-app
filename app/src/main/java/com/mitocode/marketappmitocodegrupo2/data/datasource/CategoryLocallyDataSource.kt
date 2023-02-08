package com.mitocode.marketappmitocodegrupo2.data.datasource

import com.mitocode.marketappmitocodegrupo2.data.database.DbCategory
import com.mitocode.marketappmitocodegrupo2.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryLocallyDataSource {

    val categories : Flow<List<Category>>
    suspend fun save(categories:List<DbCategory>)
    suspend fun counter() : Int

}