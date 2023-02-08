package com.mitocode.marketappmitocodegrupo2.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.data.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    private val _state = MutableStateFlow(ProductState())
    val state : StateFlow<ProductState> get() = _state

    fun getProducts(uuid: String) {

        viewModelScope.launch {

            _state.value = ProductState(isLoading = true)

            val response = withContext(Dispatchers.IO){
                productRepository.getProducts(uuid)
            }

            response.collect(){
                when(it){
                    is Result.Error -> _state.value = ProductState(error = it.message)
                    is Result.Success -> _state.value = ProductState(products = it.data)
                }
            }

        }
    }
}