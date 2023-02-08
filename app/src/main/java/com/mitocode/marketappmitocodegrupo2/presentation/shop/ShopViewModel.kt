package com.mitocode.marketappmitocodegrupo2.presentation.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketappmitocodegrupo2.data.model.RegisterCategoryRequest
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.data.repositories.CategoryRepository
import com.mitocode.marketappmitocodegrupo2.presentation.categories.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ShopViewModel @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(CategoryCreateState())
    val state: StateFlow<CategoryCreateState> get() = _state

    fun saveCategory(request: RegisterCategoryRequest) {

        viewModelScope.launch {

            _state.value = CategoryCreateState(isLoading = true)

            try {

                val response = withContext(Dispatchers.IO) {
                    categoryRepository.save(request)
                }

                response.collect() {
                    when (it) {
                        is Result.Error -> _state.value = CategoryCreateState(error = it.message)
                        is Result.Success -> _state.value =
                            CategoryCreateState(success = it.data?.message)
                    }
                }

                /*categoryRepository.save(request).flowOn(Dispatchers.IO).collect() {
                    when (it) {
                        is Result.Error -> _state.value = CategoryCreateState(error = it.message)
                        is Result.Success -> _state.value =
                            CategoryCreateState(success = it.data?.message)
                    }
                }*/


            } catch (ex: Exception) {
                _state.value = CategoryCreateState(error = ex.message)
            } finally {
                _state.value = CategoryCreateState(isLoading = false)
            }

        }

    }


}