package com.example.applenia_carbon.home.screens.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.applenia_carbon.home.data.network.response.ProductoResponse
import com.example.applenia_carbon.home.screens.modelo.CartItem

class CartViewModel : ViewModel() {

    //lista mutable para para almacenar productos
    private val _cartItems = mutableStateListOf<CartItem>()

    //obtener la lista para mostrar en las vistas
    val cartItems: List<CartItem> get() = _cartItems

    //agregar productos al carrito
    fun addProductToCart(product: ProductoResponse) {
        //busca el producto en la lista
        val existingItem = _cartItems.find { it.producto.idp == product.idp }
        if (existingItem != null) {
            //incrementa la cantidad si el producto ya está en el carrito
            val updatedItem = existingItem.copy(cantidad = existingItem.cantidad + 1)
            _cartItems[_cartItems.indexOf(existingItem)] = updatedItem
        } else {
            //agrega el nuevo producto al carrito
            _cartItems.add(CartItem(product))
        }
    }

    //quitar productos del carrito
    fun removeProductFromCart(product: ProductoResponse) {
        //busca el producto en la lista
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

    //vaciar carrito
    fun crearCart() {
        _cartItems.clear()
    }
}