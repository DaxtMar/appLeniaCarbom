package com.example.applenia_carbon.home.screens.modelo

import com.example.applenia_carbon.home.data.network.response.ProductoResponse

data class CartItem(val producto: ProductoResponse, val cantidad: Int = 1)
