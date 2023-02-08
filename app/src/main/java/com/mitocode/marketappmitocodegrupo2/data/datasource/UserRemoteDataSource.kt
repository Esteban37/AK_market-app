package com.mitocode.marketappmitocodegrupo2.data.datasource

import com.mitocode.marketappmitocodegrupo2.data.model.CreateAccountRequest
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.domain.model.User

interface UserRemoteDataSource {

    suspend fun signIn(email:String,password:String) : Result<User>
    suspend fun createAccount(request: CreateAccountRequest) : Result<User>
    suspend fun refreshToken():Boolean
}