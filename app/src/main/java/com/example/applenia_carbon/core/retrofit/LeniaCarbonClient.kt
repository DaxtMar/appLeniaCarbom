package com.example.applenia_carbon.core.retrofit

import com.example.applenia_carbon.auth.data.network.request.LoginRequest
import com.example.applenia_carbon.auth.data.network.request.RegistroRequest
import com.example.applenia_carbon.auth.data.network.response.LoginResponse
import com.example.applenia_carbon.auth.data.network.response.RegistroResponse
import com.example.applenia_carbon.home.data.network.request.PedidoRequest
import com.example.applenia_carbon.home.data.network.response.CategoriaResponse
import com.example.applenia_carbon.home.data.network.response.PedidoResponse
import com.example.applenia_carbon.home.data.network.response.ProductoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LeniaCarbonClient {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("usuarios/registro")
    suspend fun registro(@Body registroRequest: RegistroRequest): Response<RegistroResponse>

    @GET("productos")
    suspend fun listarProductos(): Response<List<ProductoResponse>>

    @GET("categorias")
    suspend fun listarCategorias(): Response<List<CategoriaResponse>>

    @GET("pedidos/usuario/{id}")
    suspend fun listarPedidos(@Path("id") id:Int): Response<List<PedidoResponse>>

    @POST("pedidos")
    suspend fun registrarPedido(@Body pedidoRequest: PedidoRequest):Response<PedidoResponse>
}