package com.example.applenia_carbon.home.domain

import com.example.applenia_carbon.home.data.network.response.ProductoResponse
import com.example.applenia_carbon.home.data.network.service.LeniaCarbonService
import com.example.applenia_carbon.home.data.repository.LeniaCarbonRepository
import javax.inject.Inject

class ProductoUseCase @Inject constructor(
    private val repository: LeniaCarbonRepository
) {
    suspend operator fun invoke(): List<ProductoResponse> {
        return repository.listarProductos()
    }
}