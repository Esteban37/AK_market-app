package com.mitocode.marketappmitocodegrupo2.presentation.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketappmitocodegrupo2.data.model.RegisterPurchaseRequest
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.domain.model.ShoppingCart
import com.mitocode.marketappmitocodegrupo2.domain.usescase.DeleteAllProductsShoppingCart
import com.mitocode.marketappmitocodegrupo2.domain.usescase.RegisterPurchase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor (
    private val registerPurchase: RegisterPurchase,
    private val deleteAllProductsShoppingCart: DeleteAllProductsShoppingCart) : ViewModel() {

    private val _state = MutableStateFlow(RegisterPurchaseCreateState())
    val state: StateFlow<RegisterPurchaseCreateState> get() = _state

    fun savePurchasedOrder(request: RegisterPurchaseRequest) {
        _state.value = RegisterPurchaseCreateState(isLoading = true)

        viewModelScope.launch {
            try {

                val response = withContext(Dispatchers.IO){
                    registerPurchase(request)
                }

                response.collect() {
                    when (it) {
                        is Result.Error -> _state.value = RegisterPurchaseCreateState(error = it.message)
                        is Result.Success -> _state.value = RegisterPurchaseCreateState(success = it.data?.message)
                    }
                }

            }catch (ex: Exception){
                _state.value = RegisterPurchaseCreateState(error = ex.toString())
            }finally {
                _state.value = RegisterPurchaseCreateState(isLoading = false)
            }
        }
    }

    fun deleteAllProducts(purchasedProducts: List<ShoppingCart>){
        viewModelScope.launch {
            try{
                _state.value = RegisterPurchaseCreateState(isLoading = true)
                deleteAllProductsShoppingCart(purchasedProducts)
            }catch (ex: Exception){
                _state.value = RegisterPurchaseCreateState(error = ex.toString())
            }finally {
                _state.value = RegisterPurchaseCreateState(isLoading = false)
            }
        }
    }
}