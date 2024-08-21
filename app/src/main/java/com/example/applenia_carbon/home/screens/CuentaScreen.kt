package com.example.applenia_carbon.home.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.applenia_carbon.MainActivity
import com.example.applenia_carbon.auth.data.network.response.LoginResponse
import com.example.applenia_carbon.auth.viewmodel.AuthViewModel
import com.example.applenia_carbon.core.utils.Event
import com.example.applenia_carbon.core.utils.separarTextos
import com.example.applenia_carbon.home.viewmodel.HomeViewModel
import com.example.applenia_carbon.core.routes.AppRoutes

@Composable
fun cuentaScreen(
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    var selectedTab by remember { mutableStateOf(0) }

    val usuario by authViewModel.obtenerPersona.observeAsState()
    val idusuario: Int = usuario!!.id
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color(0xFFC12B2A),
            contentColor = Color.White
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
            0 -> CuentaTab(usuario, navController)
            1 -> HistorialTab(homeViewModel, idusuario, navController)
        }
    }
}

@Composable
fun CuentaTab(usuario: LoginResponse?, navController: NavController) {
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
            .background(Color.Transparent) // Make the background transparent
    ) {
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
                        modifier = Modifier.size(24.dp),
                        tint = Color.Gray // Optional: set tint color for icons
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
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC12B2A), // Button background color
                    contentColor = Color.White // Text color of the button
                )
            ) {
                Text("Cerrar sesión")
            }
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
fun HistorialTab(
    homeViewModel: HomeViewModel,
    idusuario: Int,
    navController: NavController
) {

    val pedidos by homeViewModel.pedidoResponse.observeAsState(emptyList())
    LaunchedEffect(Unit) {
        homeViewModel.listarPedidos(idusuario)
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
                .padding(12.dp)
        ) {
            Text(
                "Historial de Pedidos",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (pedidos.isEmpty()) {
                Text("No tienes pedidos.")
            }
            if (pedidos.isNotEmpty()) {
                LazyColumn {
                    items(pedidos.reversed()) { pedido ->
                        val (fecha, hora) = separarTextos(pedido.horapedido, " ")
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (pedido.estado == "pendiente") Color(0xFFCF5958) else Color(
                                    0xFF448D3F
                                ),
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    navController.navigate(
                                        AppRoutes.historiaScreen.paramHistoria(
                                            pedido.id
                                        )
                                    )
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .background(Color.Red)
                                    .fillMaxWidth()
                                    .background(
                                        if (pedido.estado == "pendiente") Color(0xFFCF5958) else Color(
                                            0xFF448D3F
                                        )
                                    ),
                                horizontalAlignment = Alignment.Start,
                            ) {
                                Text(pedido.estado, color = Color.White)
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween, //extremos opuestos
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(fecha, color = Color.White)
                                    Text(hora, color = Color.White)
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
