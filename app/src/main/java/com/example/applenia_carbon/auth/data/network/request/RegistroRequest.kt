package com.example.applenia_carbon.auth.data.network.request

data class RegistroRequest(
    var nombre: String,
    var email: String,
    var password: String,
    var direccion: String,
    var telefono: String,
)