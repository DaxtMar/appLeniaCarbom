package com.example.applenia_carbon.home.domain

import com.example.applenia_carbon.home.data.network.request.PedidoRequest
import com.example.applenia_carbon.home.data.network.response.PedidoResponse
import com.example.applenia_carbon.home.data.repository.LeniaCarbonRepository
import com.example.applenia_carbon.home.data.repository.PedidoRepository
import javax.inject.Inject

class PedidoRegistrarUseCase @Inject constructor(
    private val repository: PedidoRepository
) {
    suspend operator fun invoke(pedidoRequest: PedidoRequest): PedidoResponse {
        return repository.registraPedido(pedidoRequest)
    }
}