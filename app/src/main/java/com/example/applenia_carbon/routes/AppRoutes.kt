package com.example.applenia_carbon.routes

sealed class AppRoutes (val path: String) {
    object welcomeScreen : AppRoutes("welcomeScreen")
    object loginScreen : AppRoutes("loginScreen")
    object homeScreen : AppRoutes("homeScreen/{id}") {
        fun paramHome(id: Int) = "homeScreen/${id}"
    }
    object registroScreen : AppRoutes("registroScreen")
}