package com.example.applenia_carbon.home.data.network.response

data class ProductoResponse(
    val idp: Int,
    val idc: Int,
    var nombre: String,
    var imagen: String,
    var descripcion: String,
    var precio: Double,

    )
