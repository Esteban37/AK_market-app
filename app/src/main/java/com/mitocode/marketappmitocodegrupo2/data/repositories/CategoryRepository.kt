package com.mitocode.marketappmitocodegrupo2.data.repositories

import com.mitocode.marketappmitocodegrupo2.data.database.DbCategory
import com.mitocode.marketappmitocodegrupo2.data.datasource.CategoryLocallyDataSource
import com.mitocode.marketappmitocodegrupo2.data.datasource.CategoryRemoteDataSource
import com.mitocode.marketappmitocodegrupo2.data.model.RegisterCategoryRequest
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.data.model.WrappedResponse
import com.mitocode.marketappmitocodegrupo2.data.toLocalModel
import com.mitocode.marketappmitocodegrupo2.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val categoryLocallyDataSource: CategoryLocallyDataSource) {

    val categories = categoryLocallyDataSource.categories

    suspend fun requestCategories(){

        //Obtiene datos remotos
        val categoriesRemote = categoryRemoteDataSource.getCategories()
        if(categoriesRemote.data != null){

            val countLocal = categoryLocallyDataSource.counter()
            val countRemote = categoriesRemote.data.size

            //Guardar los datos localmente
            if(countRemote > countLocal)
                categoryLocallyDataSource.save(categoriesRemote.data.toLocalModel())

        }

    }

    suspend fun save(request:RegisterCategoryRequest) : Flow<Result<WrappedResponse<Nothing>>> {
        return flow{
            emit(categoryRemoteDataSource.save(request))
        }
    }


    //Retorna la lista del servidor
    suspend fun getCategories() : Flow<Result<List<Category>>> {
        return flow {
            emit(categoryRemoteDataSource.getCategories())
        }

    }


}