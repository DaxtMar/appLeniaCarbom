package com.example.applenia_carbon.home.data.network.response

data class PedidoResponse(
    val id: Int,
    val horapedido: String,
    val estado: String,
    val total: Double,
    val usuario: Usuario,
    val metodoPago: MetodoPago,
    val detallePedido: List<DetallePedido>,
    val pagoCon: String
)

data class Usuario(
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    val direccion: String,
    val telefono: String
)


data class MetodoPago(
    val id: Int,
    val nombre: String,
    val detalles: String
)


data class DetallePedido(
    val idDetalleVenta: Int,
    val producto: Producto,
    val cantidad: Double,
    val precio: Double
)


data class Producto(
    val id: Int,
    val nombre: String,
    val image: String,
    val precio: Double,
    val descripcion: String,
    val categoria: Categoria
)


data class Categoria(
    val nombre: String,
    val imagen: String,
    val id: Int
)