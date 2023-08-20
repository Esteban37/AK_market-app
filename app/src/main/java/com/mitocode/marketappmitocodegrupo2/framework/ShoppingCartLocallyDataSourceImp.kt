package com.mitocode.marketappmitocodegrupo2.framework

import com.mitocode.marketappmitocodegrupo2.data.database.DbShoppingCart
import com.mitocode.marketappmitocodegrupo2.data.database.ShoppingCartDao
import com.mitocode.marketappmitocodegrupo2.data.datasource.ShoppingCartLocallyDataSource
import com.mitocode.marketappmitocodegrupo2.domain.model.ShoppingCart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingCartLocallyDataSourceImp @Inject constructor(private val shoppingCartDao: ShoppingCartDao): ShoppingCartLocallyDataSource {

    override val shoppingCartProductList: Flow<List<ShoppingCart>> = shoppingCartDao.getAll().map { it.toDomainModel() }

    override suspend fun save(shoppingCart: DbShoppingCart) {
        shoppingCartDao.save(shoppingCart)
    }

    override suspend fun update(shoppingCart: DbShoppingCart) {
        shoppingCartDao.update(shoppingCart)
    }

    override suspend fun delete(shoppingCart: DbShoppingCart) {
        shoppingCartDao.delete(shoppingCart)
    }

    override suspend fun deleteAll(shoppingCartList: List<DbShoppingCart>) {
        shoppingCartDao.deleteAll(shoppingCartList)
    }

    override suspend fun counter(): Int {
        return shoppingCartDao.counter()
    }

    private fun List<DbShoppingCart>.toDomainModel() : List<ShoppingCart> = map{ it.toDomainModel() }
    private fun DbShoppingCart.toDomainModel() : ShoppingCart = ShoppingCart(id, categoryCode, productCode, productDescription, /*userCode,*/ amount, price, image, total)
}