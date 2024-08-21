package com.example.applenia_carbon.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.applenia_carbon.core.utils.numConDosDecimales
import com.example.applenia_carbon.core.utils.separarTextos
import com.example.applenia_carbon.home.data.network.response.DetallePedidoResponse
import com.example.applenia_carbon.home.data.network.response.PedidoResponse
import com.example.applenia_carbon.home.viewmodel.HomeViewModel
import com.example.applenia_carbon.core.routes.AppRoutes
import kotlin.math.round

@Composable
fun historiaPorId(
    idpe: Int,
    homeViewModel: HomeViewModel,
    navController: NavController,
    iduser: Int
) {
    val pedidos: List<PedidoResponse> by homeViewModel.pedidoResponse.observeAsState(emptyList())
    val scrollState = rememberScrollState()
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .border(
                width = 3.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.Magenta, Color.Green)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.Transparent)
            .padding(8.dp) // Espacio interno del cuadro
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp)
                .verticalScroll(scrollState)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Atras"
                    )
                }
                Spacer(modifier = Modifier.width(12.dp)) // Espacio entre el icono y el texto

                Text(
                    text = "Detalle del historial",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }


            Column {
                val rpts = separarTextos(pedidoResponse!!.horapedido, " ")
                Text(text = "Estado: ${pedidoResponse?.estado}")
                Text(text = "Fecha: ${rpts[0]}")
                Text(text = "Hora: ${rpts[1]}")

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Detalles del Pedido", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                if (pedidoResponse != null) {
                    pedidoResponse.detallePedido.forEach { detallePedido ->
                        ProductoItem(detallePedido)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Total: \$${numConDosDecimales(pedidoResponse.total)}",
                    fontWeight = FontWeight.Bold
                )
                val partes = separarTextos(pedidoResponse.usuario.rutadata, "|")
                Text(
                    text = "Tiempo estimado : ${round(numConDosDecimales((partes[2]).toDouble() / 60).toDouble()).toInt()} minutos",
                )

                if (pedidoResponse.estado.equals("pendiente")) {
                    Row {
                        Button(
                            onClick = {
                                navController.navigate(AppRoutes.mapaScreen.paramRuta(partes[0]))
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFC12B2A),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Ver Ruta")
                        }
                    }
                }

            }
        }
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
            text = "${(detallePedido.cantidad).toInt()} x  ${numConDosDecimales(detallePedido.precio)}",
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "${numConDosDecimales(detallePedido.precio * detallePedido.cantidad)}",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,

            )
    }

}