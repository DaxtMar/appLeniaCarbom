package com.example.applenia_carbon.dataEjemplo

data class dataCategoria(val imageResId: Int, val name: String)

data class Producto(
    val idp:Int,
    val image: Int,
    var title: String,
    var descripcion: String,
    var precio: String,
    val index: Int = 0
)

data class CartItem(val producto: Producto, val cantidad: Int = 1)