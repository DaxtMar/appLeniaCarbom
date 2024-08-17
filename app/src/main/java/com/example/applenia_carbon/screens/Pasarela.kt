package com.example.applenia_carbon.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.applenia_carbon.home.data.network.request.DetallePedido
import com.example.applenia_carbon.home.data.network.request.PedidoRequest
import com.example.applenia_carbon.home.data.network.request.Producto
import com.example.applenia_carbon.home.viewmodel.PedidoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun pasarelaScreen(
    cartViewModel: CartViewModel,
    pedidoViewModel: PedidoViewModel,
    iduser: Int
) {
    val cartItems = cartViewModel.cartItems
    val total = cartItems.sumOf { it.producto.precio * it.cantidad }
    val totalFormateado = String.format("%.2f", total)

    val pedidoResponse by pedidoViewModel.regpedidoResponse.collectAsState()
    val error by pedidoViewModel.error.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(scrollState)
    ) {
        var selectedOption by remember { mutableStateOf(0) }

        var isLoading by remember { mutableStateOf(false) }
        var textr by remember { mutableStateOf("") }

        Text(text = "Detalles del carrito", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${cartItem.cantidad} x  ${cartItem.producto.precio}",
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${String.format("%.2f", cartItem.producto.precio * cartItem.cantidad)}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Total a Pagar: S/$totalFormateado",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(
            modifier = Modifier
                .background(Color(0xFF7A7F96))
                .height(1.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        var numeroTelefono by remember { mutableStateOf("") }
        var tarjetaCredito by remember { mutableStateOf("") }

        Text("Método de pago")
        Column {
            Row {
                RadioButton(
                    selected = selectedOption == 1,
                    onClick = { selectedOption = 1 }
                )
                Text("Yape")

                RadioButton(
                    selected = selectedOption == 2,
                    onClick = { selectedOption = 2 }
                )
                Text("Tarjeta de crédito")
            }

            when (selectedOption) {
                1 -> {
                    formYape(numeroTelefono) { newValue -> numeroTelefono = newValue }
                }

                2 -> {
                    formTarjeta(tarjetaCredito) { newValue -> tarjetaCredito = newValue }
                }
            }
        }

        val pagoconval = if (selectedOption == 1) {
            numeroTelefono
        } else {
            tarjetaCredito
        }

        Spacer(modifier = Modifier.height(6.dp))
        val coroutineScope = rememberCoroutineScope()
        Button(
            onClick = {
                val pedidoRequest = PedidoRequest(
                    idUsuario = iduser,
                    idMetodoPago = selectedOption,
                    pagocon = pagoconval.toString(),
                    detallePedido = detalle
                )
                pedidoViewModel.registrarPedido(pedidoRequest)
                isLoading = true
                coroutineScope.launch {
                    delay(1500)
                    isLoading = false
                }
            },
            modifier
            = Modifier.fillMaxWidth()
        ) {
            Text("Pagar")
        }
        pedidoResponse?.let { response ->
            if (response.id > 0) {
                textr = "Pedido Registrado con exito"
                cartViewModel.crearCart()
                pedidoViewModel.resetPedidoResponse()
            } else {
                textr = "Erorr no se registro el pedido"
            }
        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            Text(
                text = "${textr}", textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            )
        }

    }
}

@Composable
fun formYape(numeroTelefono: String, onNumeroTelefonoChange: (String) -> Unit) {
    val approvalCode = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = numeroTelefono,
            onValueChange = { newValue -> onNumeroTelefonoChange(newValue) },
            label = { Text("Ingresa tu celular Yape") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = approvalCode.value,
            onValueChange = { approvalCode.value = it },
            label = { Text("Código de aprobación") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun formTarjeta(tarjetaCredito: String, onTarjetaCreditoChange: (String) -> Unit) {
    val expiryDate = remember { mutableStateOf("") }
    val cvv = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = "Recuerda activar las compras por internet con tu banco",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = tarjetaCredito,
            onValueChange = { newValue -> onTarjetaCreditoChange(newValue) },
            label = { Text("Número de Tarjeta") },
            leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = expiryDate.value,
                onValueChange = { expiryDate.value = it },
                label = { Text("MM/AA") },
                leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = cvv.value,
                onValueChange = { cvv.value = it },
                label = { Text("CVV") },
                leadingIcon = { Icon(Icons.Default.Help, contentDescription = null) },
                modifier = Modifier.weight(1f)
            )
        }

    }
}




