package com.mitocode.marketappmitocodegrupo2.domain.usescase;

import com.mitocode.marketappmitocodegrupo2.data.repositories.ShoppingCartRepository;

import javax.inject.Inject;

class GetProductsShoppingCart @Inject
constructor(private val shoppingCartRepoitory: ShoppingCartRepository) {

    operator fun invoke() = shoppingCartRepoitory.shoppingCartProductList

}
