package com.mitocode.marketappmitocodegrupo2.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitocode.marketappmitocodegrupo2.data.model.CreateAccountRequest
import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.data.repositories.UserRepository
import com.mitocode.marketappmitocodegrupo2.domain.usescase.GetGenders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getGenders: GetGenders,
    private val userRepository: UserRepository
    ) : ViewModel() {


    private val _state = MutableLiveData<AccountState>()
    val state: LiveData<AccountState> = _state

    init {

        viewModelScope.launch {

            try {
                _state.value = AccountState(isLoading = true)

                val response = withContext(Dispatchers.IO) {
                    getGenders()
                }
                when(response){
                    is Result.Error -> {
                        _state.value = AccountState(error = response.message)
                    }
                    is Result.Success -> {
                        _state.value = AccountState(genders = response.data)
                    }
                }

            } catch (ex: Exception) {
                _state.value = AccountState(error = ex.message)
            } finally {
                _state.value = AccountState(isLoading = false)
            }


        }

    }


    fun createAccount(request:CreateAccountRequest){

        viewModelScope.launch {

            _state.value = AccountState(isLoading = true)

            try{

                val response = withContext(Dispatchers.IO){
                    userRepository.createAccount(request)
                }

                when(response){
                    is Result.Error -> {
                        _state.value = AccountState(error = response.message)
                    }
                    is Result.Success -> {
                        _state.value = AccountState(user = response.data)
                    }
                }


            }catch (ex:Exception){
                _state.value = AccountState(error = ex.message)
            }


        }

    }

}