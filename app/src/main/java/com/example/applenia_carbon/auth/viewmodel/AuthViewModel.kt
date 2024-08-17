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
/*
class AuthViewModel : ViewModel() {
    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> get() = _nombre

    private val _telefono = MutableLiveData<String>()
    val telefono: LiveData<String> get() = _telefono

    private val _direccion = MutableLiveData<String>()
    val direccion: LiveData<String> get() = _direccion

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _id = MutableLiveData<Int>()
    val id: LiveData<Int> get() = _id

    fun onLoginValueChanged(nombre: String, password: String) {
        _nombre.value = nombre
        _password.value = password
    }

    fun login() {
        val loginRequest = LoginRequest(
            nombre = _nombre.value.orEmpty(),
            password = _password.value.orEmpty()
        )

        RetrofitClient.apiServiceUsuario.login(loginRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse?.success == true) {
                            _loginSuccess.value = true
                            _id.value = loginResponse.id ?: 0
                            _nombre.value = loginResponse.nombre.orEmpty()
                            _telefono.value = loginResponse.telefono.orEmpty()
                            _direccion.value = loginResponse.direccion.orEmpty()
                            _errorMessage.value = ""
                        } else {
                            _loginSuccess.value = false
                            _errorMessage.value =
                                loginResponse?.message ?: "Usuario o contraseña incorrectos"
                        }
                    } else {
                        val errorResponse = response.errorBody()?.string()
                        val loginResponse = parseErrorResponse(errorResponse)
                        _loginSuccess.value = false
                        _errorMessage.value =
                            loginResponse?.message ?: "Usuario o contraseña incorrectos"
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _loginSuccess.value = false
                    _errorMessage.value =
                        "Error de conexión: ${t.message ?: "Por favor, verifica tu conexión a Internet"}"
                }
            })
    }

    private fun parseErrorResponse(errorBody: String?): LoginResponse? {
        return try {
            errorBody?.let {
                val gson = Gson()
                gson.fromJson(it, LoginResponse::class.java)
            }
        } catch (e: Exception) {
            null
        }
    }
}
*/
