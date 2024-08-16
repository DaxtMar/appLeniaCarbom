package com.example.applenia_carbon.home.data.repository

import com.example.applenia_carbon.home.data.network.request.PedidoRequest
import com.example.applenia_carbon.home.data.network.response.CategoriaResponse
import com.example.applenia_carbon.home.data.network.response.PedidoResponse
import com.example.applenia_carbon.home.data.network.response.ProductoResponse
import com.example.applenia_carbon.home.data.network.service.LeniaCarbonService
import javax.inject.Inject

class LeniaCarbonRepository @Inject constructor(
    private val leniaCarbonService: LeniaCarbonService
){
    suspend fun  listarCategorias(): List<CategoriaResponse>{
        return leniaCarbonService.listarCategorias()
    }

    suspend fun  listarProductos(): List<ProductoResponse>{
        return leniaCarbonService.listarProductos()
    }

    suspend fun  listarPedido(id:Int): List<PedidoResponse>{
        return leniaCarbonService.listarPedido(id)
    }



}