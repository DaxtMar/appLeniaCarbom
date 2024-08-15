package com.example.applenia_carbon.home.viewmodel

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

/*HiltViewModel
class CategoriaViewModel@Inject constructor(
    private val categoriaUseCase: CategoriaUseCase
) : ViewModel() {

    private val _categoriaResponse = MutableLiveData<List<CategoriaResponse>>()
    val categoriaResponse: LiveData<List<CategoriaResponse>> = _categoriaResponse

    init {

        listarCategorias()
    }

    fun listarCategorias() {
        viewModelScope.launch {
            val response = categoriaUseCase()
            _categoriaResponse.value = response
        }
    }
}*/