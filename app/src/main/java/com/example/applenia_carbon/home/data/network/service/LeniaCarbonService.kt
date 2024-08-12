package com.example.applenia_carbon.home.data.network.service

import com.example.applenia_carbon.core.retrofit.LeniaCarbonClient
import com.example.applenia_carbon.home.data.network.response.CategoriaResponse
import com.example.applenia_carbon.home.data.network.response.ProductoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LeniaCarbonService @Inject constructor(
    private val leniaCarbonClient: LeniaCarbonClient
) {

    suspend fun listarProductos(): List<ProductoResponse> {
        return withContext(Dispatchers.IO) {
            val response = leniaCarbonClient.listarProductos()
            response.body()!!
        }
    }

    suspend fun listarCategorias(): List<CategoriaResponse> {
        return withContext(Dispatchers.IO) {
            val response = leniaCarbonClient.listarCategorias()
            response.body()!!
        }
    }
}

