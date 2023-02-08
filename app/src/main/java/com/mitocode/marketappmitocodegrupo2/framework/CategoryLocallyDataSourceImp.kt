package com.mitocode.marketappmitocodegrupo2.framework

import com.mitocode.marketappmitocodegrupo2.data.database.CategoryDao
import com.mitocode.marketappmitocodegrupo2.data.database.DbCategory
import com.mitocode.marketappmitocodegrupo2.data.datasource.CategoryLocallyDataSource
import com.mitocode.marketappmitocodegrupo2.data.toDomainModel
import com.mitocode.marketappmitocodegrupo2.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CategoryLocallyDataSourceImp @Inject constructor(private val categoryDao : CategoryDao) : CategoryLocallyDataSource {

    override val categories: Flow<List<Category>> = categoryDao.getAll().map { it.toDomainModel() }

    override suspend fun save(categories: List<DbCategory>) {
        categoryDao.save(categories)
    }

    override suspend fun counter(): Int = categoryDao.counter()


}

