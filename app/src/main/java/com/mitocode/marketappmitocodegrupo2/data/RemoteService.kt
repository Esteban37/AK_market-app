package com.mitocode.marketappmitocodegrupo2.data

import com.mitocode.marketappmitocodegrupo2.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface RemoteService{

    //@POST("api/usuarios/login")
    //fun signIn(@Body request : LoginRequest) : Call<WrappedResponse<UserRemote>>

    @POST("api/usuarios/login")
    suspend fun signIn(@Body request : LoginRequest) : Response<WrappedResponse<UserRemote>>

    @GET("/api/usuarios/obtener-generos")
    suspend fun getGenders() : Response<WrappedListResponse<GenderRemote>>

    @POST("/api/usuarios/crear-cuenta")
    suspend fun createAccount(@Body request : CreateAccountRequest) : Response<WrappedResponse<UserRemote>>

    //@GET("/api/categorias")
    //suspend fun getCategories(@Header("Authorization") authorization:String) : Response<WrappedListResponse<CategoryRemote>>

    @GET("/api/categorias")
    suspend fun getCategories() : Response<WrappedListResponse<CategoryRemote>>

    @GET("/api/categorias/{categoriaId}/productos")
    suspend fun getProducts(
        @Path("categoriaId") categoriaId : String,
        @Header("Authorization") authorization:String
    ) : Response<WrappedListResponse<ProductRemote>>

    @POST("/api/admin/crear-categoria")
    suspend fun createCategory(
        @Header("Authorization") authorization:String,
        @Body request : RegisterCategoryRequest) : WrappedResponse<Nothing>

    @GET("/api/usuarios/renueva-token")
    suspend fun refreshToken() : Response<WrappedResponse<UserRemote>>
}