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
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.applenia_carbon.MainActivity

@Composable
fun CuentaScreen() {
    var selectedTab by remember { mutableStateOf(0) }

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
            0 -> CuentaTab()
            1 -> HistorialTab()
        }
    }
}

@Composable
fun CuentaTab() {
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var userData by remember {
        mutableStateOf(
            mapOf(
                "Nombre y Apellido" to "",
                "Número de teléfono" to "",
                "Direcciones" to "",
                "Atención al cliente" to "Comunicate al 111 o escríbenos: lenaycarbon@gmail.com"
            )
        )
    }

    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
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
                    Text(value, fontSize = 14.sp)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                // Handle Logout
                (context as? MainActivity)?.finish() // Cierra la aplicación
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
            showEditDialog = false
        }, onDismiss = { showEditDialog = false })
    }
}

@Composable
fun EditDialog(item: String, currentValue: String, onSave: (String) -> Unit, onDismiss: () -> Unit) {
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
fun HistorialTab() {
    val pedidos = listOf("Pedido 1", "Pedido 2", "Pedido 3") // Reemplaza con datos reales

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Historial de Pedidos", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        LazyColumn {
            items(pedidos) { pedido ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(pedido, modifier = Modifier.padding(16.dp))

                }
            }
        }
    }
}