package com.mitocode.marketappmitocodegrupo2.domain.usescase

import com.mitocode.marketappmitocodegrupo2.data.repositories.ShoppingCartRepository
import com.mitocode.marketappmitocodegrupo2.domain.model.ShoppingCart
import javax.inject.Inject

class UpdateShoppingCartProduct @Inject
constructor(private val shoppingCartRepoitory: ShoppingCartRepository) {

    suspend operator fun invoke(shoppingCart: ShoppingCart) = shoppingCartRepoitory.updateProductShoppingCart(shoppingCart)

}