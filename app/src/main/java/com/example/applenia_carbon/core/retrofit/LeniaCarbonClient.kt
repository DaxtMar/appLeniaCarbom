package com.example.applenia_carbon.core.retrofit

import com.example.applenia_carbon.home.data.network.response.CategoriaResponse
import com.example.applenia_carbon.home.data.network.response.ProductoResponse
import retrofit2.Response
import retrofit2.http.GET

interface LeniaCarbonClient {

    @GET("productos")
    suspend fun listarProductos(): Response<List<ProductoResponse>>

    @GET("categorias")
    suspend fun listarCategorias(): Response<List<CategoriaResponse>>
}