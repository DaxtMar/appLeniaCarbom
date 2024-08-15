package com.example.applenia_carbon.dataEjemplo

import com.example.applenia_carbon.home.data.network.response.ProductoResponse

data class dataCategoria(val idc:Int,val imageResId: Int, val name: String)

const val ipserver:String="http://192.168.1.51:8080/"

data class Producto(
    val idp:Int,
    val idc:Int,
    val image: Int,
    var title: String,
    var descripcion: String,
    var precio: Double,
    val index: Int = 0//no va
)

data class CartItem(val producto: ProductoResponse, val cantidad: Int = 1)