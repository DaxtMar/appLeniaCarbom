package com.example.applenia_carbon.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
        Text("Error Al cargar Pedido")
    }
    if (pedidos.isNotEmpty()) {
        //Text("Pedido ${idpe} | ${pedidoResponse.estado}")
        pedidoResponse = pedidos.first { it.id == idpe }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Estado: ${pedidoResponse?.estado}")
        Text(text = "Fecha y Hora: ${pedidoResponse?.horapedido}")

        Spacer(modifier = Modifier.height(8.dp))

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
            text = "${detallePedido.precio} (x${detallePedido.cantidad})",
            modifier = Modifier.weight(1f),
        )
        Text(
            text = "\$${detallePedido.precio * detallePedido.cantidad}",
            textAlign = TextAlign.End
        )
    }
}