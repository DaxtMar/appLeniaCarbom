package com.example.applenia_carbon.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.applenia_carbon.screens.viewmodel.CartViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.example.applenia_carbon.home.data.network.request.DetallePedido
import com.example.applenia_carbon.home.data.network.request.PedidoRequest
import com.example.applenia_carbon.home.data.network.request.Producto
import com.example.applenia_carbon.home.viewmodel.PedidoViewModel

//private val viewModel: PedidoViewModel = viewModels()

@Composable
fun pasarelaScreen(
    cartViewModel: CartViewModel,
    pedidoViewModel: PedidoViewModel
    /*homeViewModel: HomeViewModel*/
) {
    val cartItems = cartViewModel.cartItems
    val total = cartItems.sumOf { it.producto.precio * it.cantidad }
    val totalFormateado = String.format("%.2f", total)

    /*val viewModel: PedidoViewModel = viewModel()*/
    //val viewModel: PedidoViewModel by viewModels()
    val pedidoResponse by pedidoViewModel.regpedidoResponse.collectAsState()
    val error by pedidoViewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(text = "Datos del cliente")
        Text(text = "Mostrar Datos del usuario y poder actualizarlos")

        var selectedOption by remember { mutableStateOf(0) }
        var numeroTelefono by remember { mutableStateOf("") }
        var tarjetaCredito by remember { mutableStateOf("") }

        Column {
            Row {
                RadioButton(
                    selected = selectedOption == 1,
                    onClick = { selectedOption = 1 }
                )
                Text("Número de celular")

                RadioButton(
                    selected = selectedOption == 2,
                    onClick = { selectedOption = 2 }
                )
                Text("Tarjeta de crédito")
            }

            when (selectedOption) {
                1 -> {
                    TextField(
                        value = numeroTelefono,
                        onValueChange = { numeroTelefono = it },
                        label = { Text("Ingrese su número de celular") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
                    )
                }

                2 -> {
                    TextField(
                        value = tarjetaCredito,
                        onValueChange = { tarjetaCredito = it },
                        label = { Text("Ingrese su número de tarjeta de crédito") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
            }
        }
        val pagoconval = if (selectedOption == 1) {
            "$numeroTelefono"
        } else {
            "$tarjetaCredito"
        }

        Text(text = "Detalles del carrito")
        Spacer(modifier = Modifier.height(10.dp))

        val detalle = mutableListOf<DetallePedido>()

        cartItems.forEach { cartItem ->
            val nuevoDetalle =
                DetallePedido(
                    producto = Producto(id = cartItem.producto.idp),
                    cantidad = cartItem.cantidad
                )
            detalle.add(nuevoDetalle)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = cartItem.producto.nombre,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${cartItem.cantidad} x  ${cartItem.producto.precio}",
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${
                        String.format(
                            "%.2f",
                            cartItem.producto.precio * cartItem.cantidad
                        )
                    }",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Total a Pagar: S/$totalFormateado",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(16.dp))

        pedidoResponse?.let { response ->
            Text(text = "Pedido registrado : ${response.horapedido}")
        }
        Button(
            onClick = {
                val pedidoRequest = PedidoRequest(
                    idUsuario = 1,
                    idMetodoPago = selectedOption,
                    pagocon = pagoconval.toString(),
                    detallePedido = detalle
                )
                pedidoViewModel.registrarPedido(pedidoRequest)
            },
            modifier
            = Modifier.fillMaxWidth()
        ) {
            Text("Pagar")
        }

    }
}
