package com.example.applenia_carbon.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applenia_carbon.auth.data.network.request.LoginRequest
import com.example.applenia_carbon.auth.data.network.response.LoginResponse
//import com.example.applenia_carbon.Retrofit.RetrofitClient
import com.example.applenia_carbon.auth.domain.LoginUseCase
import com.example.applenia_carbon.core.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    var _botonLoginHabilitado = MutableLiveData<Boolean>()
    var botonLoginHabilitado: LiveData<Boolean> = _botonLoginHabilitado

    private val _loginResponse = MutableLiveData<Event<LoginResponse>>()
    val loginResponse: LiveData<Event<LoginResponse>> = _loginResponse

    private val _obtenerPersona = MutableLiveData<LoginResponse>()
    val obtenerPersona: LiveData<LoginResponse> = _obtenerPersona


    fun onLoginValueChanged(nombre: String, password: String) {
        _nombre.value = nombre
        _password.value = password
        _botonLoginHabilitado.value = habilitarBoton(nombre, password)
    }

    fun habilitarBoton(nombre: String, password: String) =
        nombre.length > 2 && password.length > 2

    fun login() {
        viewModelScope.launch {
                val response = loginUseCase(LoginRequest(nombre.value!!, password.value!!))
                _loginResponse.value = Event(response)
                _obtenerPersona.value = response
        }
    }
}
