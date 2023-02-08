package com.mitocode.marketappmitocodegrupo2.framework

import android.content.SharedPreferences
import com.mitocode.marketappmitocodegrupo2.data.model.LoginRequest
import com.mitocode.marketappmitocodegrupo2.data.RemoteService
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.data.datasource.UserRemoteDataSource
import com.mitocode.marketappmitocodegrupo2.data.model.CreateAccountRequest
import com.mitocode.marketappmitocodegrupo2.data.toDomainModel
import com.mitocode.marketappmitocodegrupo2.domain.model.User
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRemoteDataSourceImp @Inject constructor(
    private val remoteService: RemoteService,
    private val sharedPreferences: SharedPreferences) :
    UserRemoteDataSource {

    override suspend fun signIn(email: String, password: String): Result<User> {
        return try{
            val response = remoteService.signIn(LoginRequest(email,password))
            if(response.body()?.success == true){
                response.body()?.token?.let { token ->
                    sharedPreferences.edit().putString("TOKEN",token).apply()
                }
                Result.Success(data = response.body()?.data?.toDomainModel())
            }else{
                Result.Error(message = response.body()?.message!!)
            }
        }catch (ex: HttpException){
            Result.Error(message = "Encontramos un error en tu solicitud")
        }catch (ex: IOException){
            Result.Error(message = "No se pudo conectar al servidor. Revise su conexion")
        }
    }

    override suspend fun createAccount(request: CreateAccountRequest): Result<User> {

        return try{
            val response = remoteService.createAccount(request)
            if(response.body()?.success == true){
                response.body()?.token?.let { token ->
                    sharedPreferences.edit().putString("TOKEN",token).apply()
                }
                Result.Success(data = response.body()?.data?.toDomainModel())
            }else{
                Result.Error(message = response.body()?.message!!)
            }
        }catch (ex:HttpException){
            Result.Error(message = "Encontramos un error en tu solicitud")
        }catch (ex:IOException){
            Result.Error(message = "No se pudo conectar al servidor. Revise su conexion")
        }

    }

    override suspend fun refreshToken(): Boolean {
        return try{
            val response = remoteService.refreshToken()
            if(response.body()?.success == true){
                response.body()?.token?.let{ token ->
                    sharedPreferences.edit().putString("TOKEN",token).apply()
                }
                true
            }else{
                false
            }
        }catch (ex: HttpException){
            false
        }catch (ex: IOException){
            false
        }
    }

}


