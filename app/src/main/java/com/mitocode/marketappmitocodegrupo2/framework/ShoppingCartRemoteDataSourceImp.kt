package com.mitocode.marketappmitocodegrupo2.framework

import android.content.SharedPreferences
import com.mitocode.marketappmitocodegrupo2.data.RemoteService
import com.mitocode.marketappmitocodegrupo2.data.datasource.ShoppingCartRemoteDataSource
import com.mitocode.marketappmitocodegrupo2.data.model.RegisterPurchaseRequest
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.data.model.WrappedResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ShoppingCartRemoteDataSourceImp @Inject constructor(
    private val remoteService: RemoteService,
    private val sharedPreferences: SharedPreferences
) : ShoppingCartRemoteDataSource {

    override suspend fun save(request: RegisterPurchaseRequest): Result<WrappedResponse<Nothing>> {
        return try{
            val token = sharedPreferences.getString("TOKEN","") ?: ""
            val response =  remoteService.savePurchasedProductsOrder("Bearer $token", request)
            Result.Success(data = response)
        }catch (ex:HttpException){
            Result.Error(message = "Encontramos un error en tu solicitud")
        }catch (ex:IOException){
            Result.Error(message = "No se pudo conectar al servidor, revise su conexion a internet")
        }
    }

}