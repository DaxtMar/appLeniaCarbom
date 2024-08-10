package com.example.applenia_carbon.routes

sealed class AppRoutes (val path: String) {
    object welcomeScreen : AppRoutes("welcomeScreen")
    object loginScreen : AppRoutes("loginScreen")
    object homeScreen : AppRoutes("homeScreen/{id}") {
        fun paramHome(id: Int) = "homeScreen/${id}"
    }
    object registroScreen : AppRoutes("registroScreen")

    object tiendaScreen : AppRoutes("tiendaScreen")
    object categoriaScreen : AppRoutes("categoriaScreen")
    object cuentaScreen : AppRoutes("cuentaScreen")

    object detalleScreen : AppRoutes("detalleScreen/{idp}") {
        fun paramDetalle(idp: Int) = "detalleScreen/${idp}"
    }

    object carritoScreen : AppRoutes("carritoScreen")
    object pasarelaScreen : AppRoutes("pasarelaScreen")
}