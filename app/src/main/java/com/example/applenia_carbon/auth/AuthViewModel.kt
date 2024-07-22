package com.example.applenia_carbon.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AuthViewModel {
    private val _usuario = MutableLiveData<String>()
    val usuario: LiveData<String> = _usuario

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    fun onLoginValueChanged(usuario: String, password: String) {
        _usuario.value = usuario
        _password.value = password
    }

    fun login(): Boolean {
        if (usuario.value == "juan" && password.value == "123")
            return true
        else
            return false
    }
}