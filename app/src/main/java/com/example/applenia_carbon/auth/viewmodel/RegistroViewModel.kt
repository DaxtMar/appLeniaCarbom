package com.example.applenia_carbon.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.applenia_carbon.auth.data.network.request.RegistroRequest
import com.example.applenia_carbon.auth.data.network.response.RegistroResponse
import com.example.applenia_carbon.auth.domain.RegistroUseCase
import com.example.applenia_carbon.core.utils.Event
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor(
    private val registroUseCase: RegistroUseCase
) : ViewModel() {

    private val _nombre = MutableLiveData<String>("")
    val nombre: LiveData<String> = _nombre
    private val _email = MutableLiveData<String>("")
    val email: LiveData<String> = _email
    private val _telefono = MutableLiveData<String>("")
    val telefono: LiveData<String> = _telefono
    private val _direccion = MutableLiveData<String>("")
    val direccion: LiveData<String> = _direccion
    private val _password = MutableLiveData<String>("")
    val password: LiveData<String> = _password
    private val _registroResponse = MutableLiveData<Event<RegistroResponse>>()
    val registroResponse: LiveData<Event<RegistroResponse>> = _registroResponse

    fun onRegistroChanged(
        nombre: String, email: String, telefono: String,
        direccion: String, password: String
    ) {
        _nombre.value = nombre
        _email.value = email
        _telefono.value = telefono
        _direccion.value = direccion
        _password.value = password
    }

    fun setearRegistro() {
        _nombre.value = ""
        _email.value = ""
        _telefono.value = ""
        _direccion.value = ""
        _password.value = ""
    }

    fun registrarUsuario() {
        viewModelScope.launch {
            val nombre = nombre.value.orEmpty()
            val email = email.value.orEmpty()
            val telefono = telefono.value.orEmpty()
            val direccion = direccion.value.orEmpty()
            val password = password.value.orEmpty()

            if (nombre.isNotEmpty() && email.isNotEmpty() && telefono.isNotEmpty() &&
                direccion.isNotEmpty() && password.isNotEmpty()
            ) {

                val response = registroUseCase(
                    RegistroRequest(nombre, email, password, direccion, telefono)
                )

                if (response.exito) {
                    _registroResponse.value =
                        Event(RegistroResponse("Usuario registrado con éxito", true))
                    setearRegistro() // Limpia los campos después de un registro exitoso
                } else {
                    _registroResponse.value =
                        Event(RegistroResponse(response.mensaje ?: "Error desconocido", false))
                }
            } else {
                _registroResponse.value = Event(RegistroResponse("Campos incompletos", false))
            }
        }
    }
}

