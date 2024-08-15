package com.example.applenia_carbon.home.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applenia_carbon.home.data.network.request.PedidoRequest
import com.example.applenia_carbon.home.data.network.response.CategoriaResponse
import com.example.applenia_carbon.home.data.network.response.PedidoResponse
import com.example.applenia_carbon.home.data.network.response.ProductoResponse
import com.example.applenia_carbon.home.domain.CategoriaUseCase
import com.example.applenia_carbon.home.domain.PedidoRegistrarUseCase
import com.example.applenia_carbon.home.domain.PedidoUseCase
import com.example.applenia_carbon.home.domain.ProductoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productoUseCase: ProductoUseCase,
    private val categoriaUseCase: CategoriaUseCase,
    private val pedidoUseCase: PedidoUseCase,

) : ViewModel() {

    private val _productoResponse = MutableLiveData<List<ProductoResponse>>()
    val productoResponse: LiveData<List<ProductoResponse>> = _productoResponse

    private val _categoriaResponse = MutableLiveData<List<CategoriaResponse>>()
    val categoriaResponse: LiveData<List<CategoriaResponse>> get() = _categoriaResponse

    private val _pedidoResponse = MutableLiveData<List<PedidoResponse>>()
    val pedidoResponse: LiveData<List<PedidoResponse>> get() = _pedidoResponse

    init {
        listarProductos()
    }

    fun listarProductos() {
        viewModelScope.launch {
            val response = productoUseCase()
            _productoResponse.value = response
        }
    }

    fun listarCategorias() {
        viewModelScope.launch {
            val response = categoriaUseCase()
            _categoriaResponse.value = response
        }
    }

    fun listarPedidos() {
        viewModelScope.launch {
            val response = pedidoUseCase()
            _pedidoResponse.value = response
        }
    }


}