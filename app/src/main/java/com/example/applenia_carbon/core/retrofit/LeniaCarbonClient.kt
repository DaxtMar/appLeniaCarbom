package com.example.applenia_carbon.core.retrofit

import com.example.applenia_carbon.home.data.network.request.PedidoRequest
import com.example.applenia_carbon.home.data.network.response.CategoriaResponse
import com.example.applenia_carbon.home.data.network.response.PedidoResponse
import com.example.applenia_carbon.home.data.network.response.ProductoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LeniaCarbonClient {

    @GET("productos")
    suspend fun listarProductos(): Response<List<ProductoResponse>>

    @GET("categorias")
    suspend fun listarCategorias(): Response<List<CategoriaResponse>>

    @GET("pedidos/usuario/1")
    suspend fun listarPedidos(): Response<List<PedidoResponse>>

    @POST("pedidos")
    suspend fun registrarPedido(@Body pedidoRequest: PedidoRequest):Response<PedidoResponse>
}