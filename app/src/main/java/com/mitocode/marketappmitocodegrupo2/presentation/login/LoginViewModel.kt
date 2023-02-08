package com.mitocode.marketappmitocodegrupo2.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.mitocode.marketappmitocodegrupo2.data.model.Result
import com.mitocode.marketappmitocodegrupo2.data.repositories.UserRepository
import com.mitocode.marketappmitocodegrupo2.domain.usescase.RequestAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val requestAuth: RequestAuth) : ViewModel() {

    //isLoading = Mostrar o no la barra de progreso: Boolean = true/false
    //error = Mostrar el mensaje de error del servidor
    //user = Mostrar los datos del usuario

    //MutableLiveData : SET/GET - LiveData - GET
    /*private var isLoading : MutableLiveData<Boolean> = MutableLiveData()
    val _isLoading : LiveData<Boolean> = isLoading

    var error : MutableLiveData<String> = MutableLiveData()
    val _error : LiveData<String> = error

    var user : MutableLiveData<UserRemote> = MutableLiveData()
    val _user : LiveData<UserRemote> = user*/

    //LIVEDATA , FLOWS

    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> = _state


    fun signIn(email:String, password:String){

        viewModelScope.launch(Dispatchers.Main) {

            try{
                _state.value = LoginState(isLoading = true)

                val response = withContext(Dispatchers.IO){
                    requestAuth(email,password)
                }
                when(response){
                    is Result.Error -> {
                        _state.value = LoginState(error =  response.message)
                    }
                    is Result.Success -> {
                        _state.value = LoginState(user =  response.data)
                    }
                }

            }catch (ex:Exception){
                _state.value = LoginState(error = ex.message)
            }finally {
                _state.value = LoginState(isLoading = false)
            }


        }

    }

    /*fun signIn(email:String, password:String){

        viewModelScope.launch(Dispatchers.Main) {

            try{
                _state.value = LoginState(isLoading = true)

                val response = withContext(Dispatchers.IO){
                    Api.build().signIn(LoginRequest(email,password))
                }
                if(response.isSuccessful){
                    val loginResponse = response.body()
                    loginResponse?.data?.let { userRemote ->
                        _state.value = LoginState(user = userRemote)
                    }
                }else{
                   _state.value = LoginState(error = response.message())
                }
            }catch (ex:Exception){
                _state.value = LoginState(error = ex.message)
            }finally {
                _state.value = LoginState(isLoading = false)
            }


        }

    }*/

}