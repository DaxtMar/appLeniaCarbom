package com.example.applenia_carbon.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.applenia_carbon.MainActivity
import com.example.applenia_carbon.auth.data.network.response.LoginResponse
import com.example.applenia_carbon.auth.AuthViewModel
import com.example.applenia_carbon.home.data.network.response.PedidoResponse
import com.example.applenia_carbon.home.viewmodel.HomeViewModel

@Composable
fun cuentaScreen(authViewModel: AuthViewModel, homeViewModel: HomeViewModel) {
    var selectedTab by remember { mutableStateOf(0) }

    // Obtener los datos del usuario desde el ViewModel
    val usuario by authViewModel.loginResponse.observeAsState<LoginResponse>()
    val idu: Int = usuario!!.id
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Mi Cuenta") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Historial") }
            )
        }

        when (selectedTab) {
            0 -> CuentaTab(usuario)
            1 -> HistorialTab(homeViewModel, idu)
        }
    }
}

@Composable
fun CuentaTab(usuario: LoginResponse?) {
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var userData by remember {
        mutableStateOf(
            mapOf(
                "Nombre" to usuario?.nombre,
                "Número de teléfono" to usuario?.telefono,
                "Dirección" to usuario?.direccion,
                "Atención al cliente" to "Comunicate al 111 o escríbenos: lenaycarbon@gmail.com"
            )
        )
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        userData.forEach { (key, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (key != "Atención al cliente") {
                            selectedItem = key
                            showEditDialog = true
                        }
                    }
                    .padding(vertical = 16.dp)
                    .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    when (key) {
                        "Nombre y Apellido" -> Icons.Filled.Face
                        "Número de teléfono" -> Icons.Filled.Phone
                        "Direcciones" -> Icons.Filled.Home
                        else -> Icons.Filled.Help
                    },
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(key, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    if (value != null) {
                        Text(value, fontSize = 14.sp)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                // Manejar cierre de sesión
                (context as? MainActivity)?.finish() // Cierra la actividad actual
            }
        ) {
            Text("Cerrar sesión")
        }
    }

    if (showEditDialog) {
        EditDialog(selectedItem, userData[selectedItem] ?: "", onSave = { newValue ->
            userData = userData.toMutableMap().apply {
                this[selectedItem] = newValue
            }
            // Actualiza el usuario con el nuevo valor si es necesario
            showEditDialog = false
        }, onDismiss = { showEditDialog = false })
    }
}

@Composable
fun EditDialog(
    item: String,
    currentValue: String,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(currentValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar $item") },
        text = {
            Column {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Nuevo valor") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(text)
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun HistorialTab(homeViewModel: HomeViewModel, idu: Int) {

    val isDialogVisible = remember { mutableStateOf(false) }
    val pedidos by homeViewModel.pedidoResponse.observeAsState(emptyList())
    LaunchedEffect(Unit) {
        homeViewModel.listarPedidos(idu)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text("Historial de Pedidos", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        if (pedidos.isEmpty()) {
            Text("No tienes pedidos.")
        }
        if (pedidos.isNotEmpty()) {
            LazyColumn {
                items(pedidos.reversed()) { pedido ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (pedido.estado == "pendiente") Color.Red else Color.Green
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                isDialogVisible.value = true
                            }

                    ) {
                        Text(pedido.estado, modifier = Modifier.padding(8.dp))
                        Text(pedido.horapedido, modifier = Modifier.padding(8.dp))
                    }
                    miAlerDialog(
                        pedido,
                        isDialogVisible = isDialogVisible
                    )
                }
            }
        }

    }
}

@Composable
fun miAlerDialog(pedidoResponse: PedidoResponse, isDialogVisible: MutableState<Boolean>) {
    if (isDialogVisible.value) {
        AlertDialog(
            onDismissRequest = { isDialogVisible.value = false },
            title = {
                Text("Detalles del Pedido")
            },
            text = {

                Column {
                    Text(
                        text = "Estado : ${pedidoResponse.estado}",
                        Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "Hora y fecha Pedido : ${pedidoResponse.horapedido}",
                        Modifier.padding(vertical = 4.dp)
                    )
                    pedidoResponse.detallePedido.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.producto.nombre,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "$${item.precio}",
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        Text(
                            text = "Total : ${pedidoResponse.total}",
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {

                        isDialogVisible.value = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}