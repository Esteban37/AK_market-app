package com.mitocode.marketappmitocodegrupo2.data.datasource

import com.mitocode.marketappmitocodegrupo2.data.database.DbShoppingCart
import com.mitocode.marketappmitocodegrupo2.domain.model.ShoppingCart
import kotlinx.coroutines.flow.Flow

interface ShoppingCartLocallyDataSource {

    val shoppingCartProductList : Flow<List<ShoppingCart>>

    suspend fun save(shoppingCart: DbShoppingCart)
    suspend fun update(shoppingCart: DbShoppingCart)
    suspend fun delete(shoppingCart: DbShoppingCart)
    suspend fun deleteAll(shoppingCart: List<DbShoppingCart>)
    suspend fun counter() : Int

}