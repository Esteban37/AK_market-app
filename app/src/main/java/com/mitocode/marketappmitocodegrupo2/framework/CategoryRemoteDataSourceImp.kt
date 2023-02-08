package com.mitocode.marketappmitocodegrupo2.framework

import android.content.SharedPreferences
import com.mitocode.marketappmitocodegrupo2.data.RemoteService
import com.mitocode.marketappmitocodegrupo2.data.datasource.CategoryRemoteDataSource
import com.mitocode.marketappmitocodegrupo2.data.model.CategoryRemote
import com.mitocode.marketappmitocodegrupo2.data.model.RegisterCategoryRequest
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.data.model.WrappedResponse
import com.mitocode.marketappmitocodegrupo2.domain.model.Category
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CategoryRemoteDataSourceImp @Inject
constructor(private val remoteService: RemoteService,
            private val sharedPreferences: SharedPreferences) : CategoryRemoteDataSource {

    override suspend fun getCategories(): Result<List<Category>> {

        return try{

            //val token = sharedPreferences.getString("TOKEN","") ?: ""
            //val response = remoteService.getCategories("Bearer $token")
            val response = remoteService.getCategories()
            Result.Success(data = response.body()?.data?.toDomainModel())

        }catch (ex:HttpException){
            Result.Error(message = "Oops, something went wrong")
        }catch (ex:IOException){
            Result.Error(message = "Couldn't reach server, check your internet connection")
        }

    }

    override suspend fun save(request: RegisterCategoryRequest): Result<WrappedResponse<Nothing>> {
        return try{
            val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImZpcmViYXNlVG9rZW5zIjpbXSwiY2VsdWxhciI6IiIsImdlbmVybyI6Ik0iLCJucm9Eb2MiOiIiLCJ0aXBvIjoiQSIsIl9pZCI6IjYwOTk1NmZmNTY0YjUxMDAxNTgxOTgzMiIsInV1aWQiOiIyZDU4NDE5OS05MzU2LTRiYTktYjc4ZC05ZmU3YThlNWE2ZDYiLCJub21icmVzIjoiQWRtaW4iLCJhcGVsbGlkb3MiOiJHZW5lcmFsIiwiZW1haWwiOiJhZG1pbkBnbWFpbC5jb20iLCJwYXNzd29yZCI6IiQyYSQxMCQuaUFLLjFtMktQc0kwWWxRaVppUFNlN1BxNGg0M0h3TGlXRzlOU1hpbEg3NG1VZlI4UHEyLiIsImNvbXByYXMiOltdLCJmZWNoYUNyZWFjaW9uIjoiMjAyMS0wNS0xMFQxNTo1MzozNS43NDRaIiwiX192IjowfSwiaWF0IjoxNjczNTcyMTcwLCJleHAiOjE2NzM2MTUzNzB9.AwVqNQRy1PkniUqyKZe-7nRIU-DPGfcZS6P-pP6Hs_s"
            val response = remoteService.createCategory("Bearer $token",request)
            Result.Success(data = response)
        }catch (ex:HttpException){
            Result.Error(message = "Oops, something went wrong")
        }catch (ex:IOException){
            Result.Error(message = "Couldn't reach server, check your internet connection")
        }

    }
}

//List<CategoryRemote> -> List<Category>  it CategoryRemote
private fun List<CategoryRemote>.toDomainModel() : List<Category> = map{ it.toDomainModel() }

private fun CategoryRemote.toDomainModel() : Category = Category(uuid,name,cover)