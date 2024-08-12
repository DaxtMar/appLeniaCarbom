package com.example.applenia_carbon.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applenia_carbon.home.data.network.response.CategoriaResponse
import com.example.applenia_carbon.home.data.network.response.ProductoResponse
import com.example.applenia_carbon.home.domain.CategoriaUseCase
import com.example.applenia_carbon.home.domain.ProductoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productoUseCase: ProductoUseCase,
    private val categoriaUseCase: CategoriaUseCase
) : ViewModel() {

    private val _productoResponse = MutableLiveData<List<ProductoResponse>>()
    val productoResponse: LiveData<List<ProductoResponse>> = _productoResponse

    //private val _categoriaResponse = MutableLiveData<List<CategoriaResponse>>()
    //val categoriaResponse: LiveData<List<CategoriaResponse>> = _categoriaResponse

    private val _categoriaResponse = MutableLiveData<List<CategoriaResponse>>()
    val categoriaResponse: LiveData<List<CategoriaResponse>> get() = _categoriaResponse

    init {
        listarProductos()
        //listarCategorias()
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
}