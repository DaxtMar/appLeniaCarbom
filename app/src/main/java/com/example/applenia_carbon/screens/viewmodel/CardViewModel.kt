package com.example.applenia_carbon.screens.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.applenia_carbon.dataEjemplo.CartItem
import com.example.applenia_carbon.dataEjemplo.Producto

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    fun addProductToCart(product: Producto) {
        val existingItem = _cartItems.find { it.producto.idp == product.idp }
        if (existingItem != null) {
            // Incrementa la cantidad si el producto ya está en el carrito
            val updatedItem = existingItem.copy(cantidad = existingItem.cantidad + 1)
            _cartItems[_cartItems.indexOf(existingItem)] = updatedItem
        } else {
            // Agrega el nuevo producto al carrito
            _cartItems.add(CartItem(product))
        }
    }

    fun removeProductFromCart(product: Producto) {
        val existingItem = _cartItems.find { it.producto.idp == product.idp }
        if (existingItem != null) {
            if (existingItem.cantidad > 1) {
                val updatedItem = existingItem.copy(cantidad = existingItem.cantidad - 1)
                _cartItems[_cartItems.indexOf(existingItem)] = updatedItem
            } else {
                _cartItems.remove(existingItem)
            }
        }
    }
}