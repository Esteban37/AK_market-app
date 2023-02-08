package com.mitocode.marketappmitocodegrupo2.data.repositories


import com.mitocode.marketappmitocodegrupo2.data.datasource.UserRemoteDataSource
import com.mitocode.marketappmitocodegrupo2.data.model.CreateAccountRequest
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.domain.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) {

    suspend fun signIn(email:String,password:String): Result<User> = userRemoteDataSource.signIn(email,password)

    suspend fun createAccount(request: CreateAccountRequest) = userRemoteDataSource.createAccount(request)

    suspend fun refreshToken() = userRemoteDataSource.refreshToken()

}


/*return try{
        val response = Api.build().signIn(LoginRequest(email,password))
        Result.Success(data = response.body()?.data)
    }catch (ex:HttpException){
        Result.Error(message = "Encontramos un error en tu solicitud")
    }catch (ex:IOException){
        Result.Error(message = "No se pudo conectar al servidor. Revise su conexion")
    }*/