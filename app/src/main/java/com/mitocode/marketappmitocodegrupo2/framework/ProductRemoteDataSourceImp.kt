package com.mitocode.marketappmitocodegrupo2.framework

import android.content.SharedPreferences
import com.mitocode.marketappmitocodegrupo2.data.RemoteService
import com.mitocode.marketappmitocodegrupo2.data.datasource.ProductRemoteDataSource
import com.mitocode.marketappmitocodegrupo2.data.model.ProductRemote
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.domain.model.Product
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRemoteDataSourceImp @Inject constructor(
    private val remoteService: RemoteService,
    private val sharedPreferences: SharedPreferences) : ProductRemoteDataSource {

    override suspend fun getProducts(uuid: String): Result<List<Product>> {
       return try{
           val token = sharedPreferences.getString("TOKEN","") ?: ""
           val response = remoteService.getProducts(uuid,"Bearer $token")
           Result.Success(data = response.body()?.data?.toDomainModel())

       }catch (ex:HttpException){
           Result.Error(message = "Oops, something went wrong")
       }catch (ex:IOException){
           Result.Error(message = "Couldn't reach server, check your internet connection")
       }
    }
}

private fun List<ProductRemote>.toDomainModel() : List<Product> = map { it.toDomainModel() }
private fun ProductRemote.toDomainModel() : Product = Product(quantity,feautures,code,description,images,price,stock,uuid)
