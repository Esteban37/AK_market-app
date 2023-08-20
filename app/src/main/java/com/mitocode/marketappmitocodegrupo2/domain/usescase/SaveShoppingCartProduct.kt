package com.mitocode.marketappmitocodegrupo2.domain.usescase

import com.mitocode.marketappmitocodegrupo2.data.repositories.ShoppingCartRepository
import com.mitocode.marketappmitocodegrupo2.domain.model.ShoppingCart
import javax.inject.Inject

class SaveShoppingCartProduct @Inject constructor(private val purchasedProductRepository: ShoppingCartRepository) {

    suspend operator fun invoke(purchasedProduct: ShoppingCart) = purchasedProductRepository.saveProductShoppingCart(purchasedProduct)

}