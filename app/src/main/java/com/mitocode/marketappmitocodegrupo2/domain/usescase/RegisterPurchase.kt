package com.mitocode.marketappmitocodegrupo2.domain.usescase

import com.mitocode.marketappmitocodegrupo2.data.model.RegisterPurchaseRequest
import com.mitocode.marketappmitocodegrupo2.data.repositories.ShoppingCartRepository
import javax.inject.Inject

class RegisterPurchase @Inject constructor(private val shoppingCartRepository: ShoppingCartRepository) {

    suspend operator fun invoke(request: RegisterPurchaseRequest) = shoppingCartRepository.registerPurchase(request)

}