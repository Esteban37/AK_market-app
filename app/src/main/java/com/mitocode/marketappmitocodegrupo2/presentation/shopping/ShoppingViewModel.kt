package com.mitocode.marketappmitocodegrupo2.presentation.shopping;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketappmitocodegrupo2.domain.model.ShoppingCart
import com.mitocode.marketappmitocodegrupo2.domain.usescase.DeleteAllProductsShoppingCart
import com.mitocode.marketappmitocodegrupo2.domain.usescase.DeleteShoppingCartProduct
import com.mitocode.marketappmitocodegrupo2.domain.usescase.GetProductsShoppingCart
import com.mitocode.marketappmitocodegrupo2.domain.usescase.UpdateShoppingCartProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject
constructor(
    private val getProductsShoppingCart: GetProductsShoppingCart,
    private val updateShoppingCartProduct: UpdateShoppingCartProduct,
    private val deleteShoppingCartProduct: DeleteShoppingCartProduct,
    private val deleteAllProductsShoppingCart: DeleteAllProductsShoppingCart): ViewModel() {

    private val _state: MutableStateFlow<OrdersState> = MutableStateFlow(OrdersState.Init)
    val state: StateFlow<OrdersState> = _state

    fun loadPurchasedProducts(){
        viewModelScope.launch {
            getProductsShoppingCart()
                .collect{ shoppingCartList ->
                    _state.value = OrdersState.Success(shoppingCartList)
                    _state.value = OrdersState.Amount(shoppingCartList.sumOf { it.total })
                }
        }
    }

    fun updateProduct(shoppingCart: ShoppingCart){
        viewModelScope.launch {
            try {
                _state.value = OrdersState.IsLoading(true)
                updateShoppingCartProduct(shoppingCart)
                loadPurchasedProducts()
            }catch (ex: Exception){
                _state.value = OrdersState.Error(ex.toString())
            }finally {
                _state.value = OrdersState.IsLoading(false)
            }
        }
    }

    fun deleteProduct(shoppingCart: ShoppingCart){
        viewModelScope.launch {
            try {
                _state.value = OrdersState.IsLoading(true)
                deleteShoppingCartProduct(shoppingCart)
                loadPurchasedProducts()
            }catch (ex: Exception){
                _state.value = OrdersState.Error(ex.toString())
            }finally {
                _state.value = OrdersState.IsLoading(false)
            }
        }
    }

    fun deleteAllProducts(purchasedProducts: List<ShoppingCart>){
        viewModelScope.launch {
            try {
                _state.value = OrdersState.IsLoading(true)
                deleteAllProductsShoppingCart(purchasedProducts)
                loadPurchasedProducts()
            }catch (ex: Exception){
                _state.value = OrdersState.Error(ex.toString())
            }finally {
                _state.value = OrdersState.IsLoading(false)
            }
        }
    }

    sealed class OrdersState(){
        object Init: OrdersState()
        data class IsLoading(val isLoading: Boolean): OrdersState()
        data class Success(val purchasedProducts: List<ShoppingCart>): OrdersState()
        data class Error(val rawResponse: String): OrdersState()
        data class Amount(val amount: Double): OrdersState()
    }

}
