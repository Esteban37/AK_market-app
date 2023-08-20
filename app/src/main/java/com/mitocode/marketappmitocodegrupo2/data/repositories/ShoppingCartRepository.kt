package com.mitocode.marketappmitocodegrupo2.data.repositories

import com.mitocode.marketappmitocodegrupo2.data.database.DbShoppingCart
import com.mitocode.marketappmitocodegrupo2.data.datasource.ShoppingCartLocallyDataSource
import com.mitocode.marketappmitocodegrupo2.data.datasource.ShoppingCartRemoteDataSource
import com.mitocode.marketappmitocodegrupo2.data.model.RegisterPurchaseRequest
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.data.model.WrappedResponse
import com.mitocode.marketappmitocodegrupo2.domain.model.ShoppingCart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShoppingCartRepository @Inject constructor(
    private val shoppingCartRemoteDataSource:ShoppingCartRemoteDataSource,
    private val shoppingCartLocallyDataSource:ShoppingCartLocallyDataSource) {

    val shoppingCartProductList = shoppingCartLocallyDataSource.shoppingCartProductList

    suspend fun saveProductShoppingCart(shoppingCart: ShoppingCart) {
        shoppingCartLocallyDataSource.save(shoppingCart.toLocalModel())
    }

    suspend fun updateProductShoppingCart(shoppingCart: ShoppingCart) {
        shoppingCartLocallyDataSource.update(shoppingCart.toLocalModel())
    }

    suspend fun deleteProductShoppingCart(shoppingCart: ShoppingCart) {
        shoppingCartLocallyDataSource.delete(shoppingCart.toLocalModel())
    }

    suspend fun deleteAll(purchasedProducts: List<ShoppingCart>) {
        shoppingCartLocallyDataSource.deleteAll(purchasedProducts.toLocalModel())
    }

    suspend fun registerPurchase(request: RegisterPurchaseRequest) : Flow<Result<WrappedResponse<Nothing>>> {
        return flow{
            emit(shoppingCartRemoteDataSource.save(request))
        }
    }

    private fun ShoppingCart.toLocalModel(): DbShoppingCart = DbShoppingCart(id = id, categoryCode =  categoryCode, productCode = productCode,
        productDescription = productDescription, /*userCode = userCode,*/ amount = amount, price = price, image = image, total = total)

    private fun List<ShoppingCart>.toLocalModel() : List<DbShoppingCart> = map{ it.toLocalModel() }
}