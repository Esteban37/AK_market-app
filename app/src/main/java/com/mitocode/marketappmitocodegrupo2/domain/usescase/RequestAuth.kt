package com.mitocode.marketappmitocodegrupo2.domain.usescase

import com.mitocode.marketappmitocodegrupo2.data.repositories.UserRepository
import javax.inject.Inject

class RequestAuth @Inject constructor(private val userRepository: UserRepository)  {

    suspend operator fun invoke(email:String,password:String) = userRepository.signIn(email,password)

}