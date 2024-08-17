package com.example.applenia_carbon.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.applenia_carbon.home.data.network.request.DetallePedido
import com.example.applenia_carbon.home.data.network.request.Producto
import com.example.applenia_carbon.home.data.network.response.DetallePedidoResponse
import com.example.applenia_carbon.home.data.network.response.PedidoResponse
import com.example.applenia_carbon.home.viewmodel.HomeViewModel

@Composable
fun historiaPorId(
    idpe: Int,
    homeViewModel: HomeViewModel,
    navController: NavController,
    iduser: Int
) {
    val pedidos: List<PedidoResponse> by homeViewModel.pedidoResponse.observeAsState(emptyList())
    LaunchedEffect(Unit) {
        homeViewModel.listarPedidos(iduser)
    }
    var pedidoResponse: PedidoResponse? = null
    if (pedidos.isEmpty()) {
        Text("Error al cargar el Historial")
    }
    if (pedidos.isNotEmpty()) {
        pedidoResponse = pedidos.first { it.id == idpe }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "atras"
                )
            }
            Spacer(modifier = Modifier.width(12.dp)) // Espacio entre el icono y el texto
            Text(
                text = "Detalle del historial",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,

                )
        }

        Text(text = "Estado: ${pedidoResponse?.estado}")
        Text(text = "Fecha y Hora: ${pedidoResponse?.horapedido}")

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Detalles del Pedido", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        if (pedidoResponse != null) {
            pedidoResponse.detallePedido.forEach { detallePedido ->
                ProductoItem(detallePedido)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Total: \$${pedidoResponse?.total}",
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProductoItem(detallePedido: DetallePedidoResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {

        Text(
            text = detallePedido.producto.nombre,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "${detallePedido.cantidad} x  ${detallePedido.producto.precio}",
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "${
                String.format(
                    "%.2f",
                    detallePedido.producto.precio * detallePedido.cantidad
                )
            }",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,

        )
    }

}