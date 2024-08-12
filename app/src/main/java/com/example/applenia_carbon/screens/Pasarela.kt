package com.example.applenia_carbon.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.applenia_carbon.screens.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@Composable
fun pasarelaScreen(
    cartViewModel: CartViewModel
) {
    val cartItems = cartViewModel.cartItems
    val total = cartItems.sumOf { it.producto.precio * it.cantidad }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(text = "Datos del cliente")
        Text(text = "Aca puede cambiar la direccion...")
        Text(text = "falta agregar metodo de pago aca")
        Text(text = "Detalles del carrito")
        Spacer(modifier = Modifier.height(26.dp))
        cartItems.forEach { cartItem ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = cartItem.producto.nombre,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "${cartItem.cantidad} x ${cartItem.producto.precio}",
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "${cartItem.producto.precio * cartItem.cantidad}".toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Total: $$total",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                /*metodo pagar*/
            },
            modifier
            = Modifier.fillMaxWidth()
        ) {
            Text("Pagar")
        }

    }
}
