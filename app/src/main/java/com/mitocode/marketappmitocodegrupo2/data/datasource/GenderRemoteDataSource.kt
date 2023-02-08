package com.mitocode.marketappmitocodegrupo2.data.datasource

import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.domain.model.Gender

interface GenderRemoteDataSource {

    suspend fun populateGenders() : Result<List<Gender>>

}