package com.example.applenia_carbon.core.utils

fun separarTextos(texto: String, separador: String): List<String> {
    val parts = texto.split(separador)
    return parts
}

fun numConDosDecimales(numero: Double): String {
    return String.format("%.2f", numero)
}