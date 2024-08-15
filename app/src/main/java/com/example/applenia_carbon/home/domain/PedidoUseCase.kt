package com.example.applenia_carbon.home.domain

import com.example.applenia_carbon.home.data.network.request.PedidoRequest
import com.example.applenia_carbon.home.data.network.response.CategoriaResponse
import com.example.applenia_carbon.home.data.network.response.PedidoResponse
import com.example.applenia_carbon.home.data.repository.LeniaCarbonRepository
import javax.inject.Inject

class PedidoUseCase @Inject constructor(
    private val repository: LeniaCarbonRepository
) {
    suspend operator fun invoke(): List<PedidoResponse> {
        return repository.listarPedido()
    }


}