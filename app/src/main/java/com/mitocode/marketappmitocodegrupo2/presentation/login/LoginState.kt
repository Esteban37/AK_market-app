package com.mitocode.marketappmitocodegrupo2.presentation.login

import com.mitocode.marketappmitocodegrupo2.domain.model.User

data class LoginState(
    val isLoading:Boolean = false,
    val error:String? = null,
    val user: User? = null
)
