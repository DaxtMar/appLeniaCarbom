package com.example.applenia_carbon.routes

sealed class AppRoutes(val path: String, val nombre: String = "") {
    object welcomeScreen : AppRoutes("welcomeScreen")
    object loginScreen : AppRoutes("loginScreen")
    object homeScreen : AppRoutes("homeScreen/{id}") {
        fun paramHome(id: Int) = "homeScreen/${id}"
    }

    object registroScreen : AppRoutes("registroScreen")

    //    object tiendaScreen : AppRoutes("tiendaScreen/{categoriaIndex}", "Tienda") {
//        fun createRoute(categoriaIndex: Int? = null): String {
//            return "tiendaScreen/${categoriaIndex ?: "default"}"
//        }
//    }
    object tiendaScreen : AppRoutes("tiendaScreen", "Tienda")

    object categoriaScreen : AppRoutes("categoriaScreen", "Categoria")
    object cuentaScreen : AppRoutes("cuentaScreen", "Cuenta")
    
    object detalleScreen : AppRoutes("detalleScreen/{idp}") {
        fun paramDetalle(idp: Int) = "detalleScreen/${idp}"
    }

    object carritoScreen : AppRoutes("carritoScreen", "Carrito")
    object pasarelaScreen : AppRoutes("pasarelaScreen")
}

fun opcionesApp(): List<AppRoutes> {
    return listOf(
        AppRoutes.tiendaScreen,
        AppRoutes.categoriaScreen,
        AppRoutes.cuentaScreen,
        AppRoutes.carritoScreen
    )
}