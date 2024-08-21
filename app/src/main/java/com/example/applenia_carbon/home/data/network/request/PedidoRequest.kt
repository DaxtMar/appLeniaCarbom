package com.example.applenia_carbon.home.data.network.request

data class PedidoRequest(
    val idUsuario: Int,
    val idMetodoPago: Int,
    val pagocon:String,
    val detallePedido: List<DetallePedido>
)

data class DetallePedido(
    val producto: Producto,
    val cantidad: Int
)

data class Producto(
    val id: Int
)