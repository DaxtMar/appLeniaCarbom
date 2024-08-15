package com.example.applenia_carbon.home.data.repository

import com.example.applenia_carbon.home.data.network.request.PedidoRequest
import com.example.applenia_carbon.home.data.network.response.PedidoResponse
import com.example.applenia_carbon.home.data.network.service.LeniaCarbonService
import com.example.applenia_carbon.home.data.network.service.PedidoService
import javax.inject.Inject

class PedidoRepository @Inject constructor(
    private val pedidoService: PedidoService
){
    suspend fun registraPedido(pedidoRequest: PedidoRequest): PedidoResponse {
        return pedidoService.registrarPedido(pedidoRequest)
    }
}