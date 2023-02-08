package com.mitocode.marketappmitocodegrupo2.framework

import com.mitocode.marketappmitocodegrupo2.data.RemoteService
import com.mitocode.marketappmitocodegrupo2.data.datasource.GenderRemoteDataSource
import com.mitocode.marketappmitocodegrupo2.data.model.GenderRemote
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.domain.model.Gender
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GenderRemoteDataSourceImp @Inject constructor(private val remoteService: RemoteService) : GenderRemoteDataSource {

    override suspend fun populateGenders(): Result<List<Gender>> {

        return try{

            val response = remoteService.getGenders()
            Result.Success(data = response.body()?.data?.toDomainModel())

        }catch (ex:HttpException){
            Result.Error(message = "Encontramos un error en tu solicitud")
        }catch (ex:IOException){
            Result.Error(message = "No se pudo conectar al servidor, revise su conexion a internet")
        }
    }
}

//List<GenderRemote> -> List<Gender>
private fun List<GenderRemote>.toDomainModel() : List<Gender> = map { it.toDomainModel() }
private fun GenderRemote.toDomainModel() : Gender = Gender(code,description)