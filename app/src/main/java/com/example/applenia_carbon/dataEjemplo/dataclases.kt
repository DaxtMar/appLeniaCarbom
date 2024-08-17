package com.example.applenia_carbon.dataEjemplo

import com.example.applenia_carbon.home.data.network.response.ProductoResponse

const val ipserver: String = "http://192.168.1.51:8080/"

/*
data class dataCategoria(val idc:Int,val imageResId: Int, val name: String)
data class Producto(
    val idp:Int,
    val idc:Int,
    val image: Int,
    var title: String,
    var descripcion: String,
    var precio: Double,
)*/

data class CartItem(val producto: ProductoResponse, val cantidad: Int = 1)