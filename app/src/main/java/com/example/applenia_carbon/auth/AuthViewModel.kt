

package com.example.applenia_carbon.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.applenia_carbon.Models.LoginRequest
import com.example.applenia_carbon.Models.LoginResponse
import com.example.applenia_carbon.Retrofit.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {
    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> get() = _nombre

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

        RetrofitClient.apiServiceUsuario.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.success == true) {
                        _loginSuccess.value = true
                        _id.value = loginResponse.id ?: 0 // Asume 0 si id es null
                        _errorMessage.value = "" // Limpiar el mensaje de error en caso de éxito
                    } else {
                        _loginSuccess.value = false
                        _errorMessage.value = loginResponse?.message ?: "Usuario o contraseña incorrectos"
                    }
                } else {
                    // Esto manejará el caso de códigos HTTP como 401
                    val errorResponse = response.errorBody()?.string()
                    val loginResponse = parseErrorResponse(errorResponse)
                    _loginSuccess.value = false
                    _errorMessage.value = loginResponse?.message ?: "Usuario o contraseña incorrectos"
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginSuccess.value = false
                _errorMessage.value = "Error de conexión: ${t.message ?: "Por favor, verifica tu conexión a Internet"}"
            }
        })
    }

    // Método para analizar la respuesta de error en JSON
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


//package com.example.applenia_carbon.auth
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//
//class AuthViewModel {
//    private val _usuario = MutableLiveData<String>()
//    val usuario: LiveData<String> = _usuario
//
//    private val _password = MutableLiveData<String>()
//    val password: LiveData<String> = _password
//
//    fun onLoginValueChanged(usuario: String, password: String) {
//        _usuario.value = usuario
//        _password.value = password
//    }
//
//    fun login(): Boolean {
//        if (usuario.value == "juan" && password.value == "123")
//            return true
//        else
//            return false
//    }
//}