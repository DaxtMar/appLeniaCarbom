package com.example.applenia_carbon.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applenia_carbon.home.data.network.request.PedidoRequest
import com.example.applenia_carbon.home.data.network.response.PedidoResponse
import com.example.applenia_carbon.home.domain.PedidoRegistrarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PedidoViewModel @Inject constructor(
    private val pedidoRegistrarUseCase: PedidoRegistrarUseCase
) : ViewModel() {

    private val _regpedidoResponse = MutableStateFlow<PedidoResponse?>(null)
    val regpedidoResponse: StateFlow<PedidoResponse?> get() = _regpedidoResponse

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun registrarPedido(pedidoRequest: PedidoRequest) {
        viewModelScope.launch {
            try {
                val response = pedidoRegistrarUseCase(pedidoRequest)
                _regpedidoResponse.value = response
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun resetPedidoResponse() {
        _regpedidoResponse.value = null
    }
}