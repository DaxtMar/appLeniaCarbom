package com.example.applenia_carbon.home.data.network.service

import com.example.applenia_carbon.core.retrofit.LeniaCarbonClient
import com.example.applenia_carbon.home.data.network.request.PedidoRequest
import com.example.applenia_carbon.home.data.network.response.PedidoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PedidoService @Inject constructor(
    private val leniaCarbonClient: LeniaCarbonClient
) {
    suspend fun registrarPedido(pedidoRequest: PedidoRequest): PedidoResponse {
        return withContext(Dispatchers.IO) {
            val response = leniaCarbonClient.registrarPedido(pedidoRequest)
            response.body()!!
        }
    }
}