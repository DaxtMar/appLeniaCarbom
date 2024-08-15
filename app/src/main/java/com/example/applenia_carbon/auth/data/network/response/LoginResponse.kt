package com.example.applenia_carbon.auth.data.network.response

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val id: Int,
    val nombre: String,
    val telefono: String,
    val direccion: String
)
