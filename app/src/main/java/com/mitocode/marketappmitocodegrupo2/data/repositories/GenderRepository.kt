package com.mitocode.marketappmitocodegrupo2.data.repositories

import com.mitocode.marketappmitocodegrupo2.data.datasource.GenderRemoteDataSource
import javax.inject.Inject

class GenderRepository @Inject constructor(private val genderRemoteDataSource: GenderRemoteDataSource) {

    suspend fun getGenders() = genderRemoteDataSource.populateGenders()

}