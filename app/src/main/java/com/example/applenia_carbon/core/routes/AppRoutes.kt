package com.example.applenia_carbon.core.routes

sealed class AppRoutes(val path: String, val nombre: String = "") {
    object welcomeScreen : AppRoutes("welcomeScreen")
    object loginScreen : AppRoutes("loginScreen")

    //object homeScreen : AppRoutes("homeScreen")

    object homeScreen : AppRoutes("homeScreen/{id}") {
        fun paramHome(id: Int) = "homeScreen/${id}"
    }

    object registroScreen : AppRoutes("registroScreen")

    object tiendaScreen : AppRoutes("tiendaScreen/{categoryId}", "Tienda") {
        fun createRoute(categoriaIndex: Int? = null): String {
            return "tiendaScreen/${categoriaIndex ?: "default"}"
        }
    }

    //object tiendaScreen : AppRoutes("tiendaScreen", "Tienda")
    object categoriaScreen : AppRoutes("categoriaScreen", "Categoria")
    object cuentaScreen : AppRoutes("cuentaScreen", "Cuenta")

    object detalleScreen : AppRoutes("detalleScreen/{idp}") {
        fun paramDetalle(idp: Int) = "detalleScreen/${idp}"
    }

    object carritoScreen : AppRoutes("carritoScreen", "Carrito")
    object pasarelaScreen : AppRoutes("pasarelaScreen")
    object historiaScreen : AppRoutes("historiaScreen/{id}") {
        fun paramHistoria(id: Int) = "historiaScreen/${id}"
    }
    //mapa
    object mapaScreen : AppRoutes("mapaScreen/{texto}") {
        fun paramRuta(texto: String) = "mapaScreen/${texto}"
    }
}
