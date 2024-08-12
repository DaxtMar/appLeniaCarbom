package com.example.applenia_carbon.home.domain

import com.example.applenia_carbon.home.data.network.response.CategoriaResponse
import com.example.applenia_carbon.home.data.network.service.LeniaCarbonService
import javax.inject.Inject

class CategoriaUseCase @Inject constructor(
    private val repository: LeniaCarbonService
) {
    suspend operator fun invoke(): List<CategoriaResponse> {
        return repository.listarCategorias()
    }
}