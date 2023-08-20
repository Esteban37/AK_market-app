package com.mitocode.marketappmitocodegrupo2.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketappmitocodegrupo2.domain.usescase.GetCategories
import com.mitocode.marketappmitocodegrupo2.domain.usescase.SaveCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategories: GetCategories,
    private val saveCategories: SaveCategories
) : ViewModel() {

    //LIVEDATA - FLOW
    private val _state = MutableStateFlow(CategoryState())
    val state: StateFlow<CategoryState> get() = _state


    init {

        viewModelScope.launch {

            _state.value = CategoryState(isLoading = true)

            try {

                getCategories().catch {
                    _state.value = CategoryState(error = it.message)
                }.collect() {
                    _state.value = CategoryState(categories = it)
                }

            } catch (ex: Exception) {
                _state.value = CategoryState(error = ex.message)
            } finally {
                _state.value = CategoryState(isLoading = false)
            }

        }

    }

    fun onUIReady() {
        viewModelScope.launch {
            _state.value = CategoryState(isLoading = true)
            saveCategories()
            _state.value = CategoryState(isLoading = false)
        }
    }

    /*fun populateCategories(){

        viewModelScope.launch {

            _state.value = CategoryState(isLoading = true)

            try{

                val response = withContext(Dispatchers.IO){
                    getCategories()
                }

                response.collect(){

                    when(it){
                        is Result.Error -> {
                            _state.value = CategoryState(error = it.message)
                        }
                        is Result.Success -> {
                            _state.value = CategoryState(categories = it.data)
                        }
                    }

                }


            }catch (ex:Exception){
                _state.value = CategoryState(error = ex.message)
            }finally {
                _state.value = CategoryState(isLoading = false)
            }

        }


    }*/


}