package com.mitocode.marketappmitocodegrupo2.presentation.account

import com.mitocode.marketappmitocodegrupo2.domain.model.Gender
import com.mitocode.marketappmitocodegrupo2.domain.model.User

data class AccountState(
    val isLoading : Boolean = false,
    val error:String? = null,
    val genders:List<Gender>? = null,
    val user: User? = null
)
