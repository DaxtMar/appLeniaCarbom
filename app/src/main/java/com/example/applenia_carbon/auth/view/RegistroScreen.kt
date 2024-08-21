package com.example.applenia_carbon.auth.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.applenia_carbon.auth.viewmodel.RegistroViewModel
import com.example.applenia_carbon.core.routes.AppRoutes

@Composable
fun RegistroScreen(
    registroViewModel: RegistroViewModel,
    navController: NavController
) {
    val nombre by registroViewModel.nombre.observeAsState("")
    val email by registroViewModel.email.observeAsState("")
    val telefono by registroViewModel.telefono.observeAsState("")
    val direccion by registroViewModel.direccion.observeAsState("")
    val password by registroViewModel.password.observeAsState("")
    val registroResponse by registroViewModel.registroResponse.observeAsState()

    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.Blue, Color.Cyan)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .border(2.dp, Color.White, shape = MaterialTheme.shapes.medium)
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Registro de Usuario",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    color = Color.White
                )
                TextField(
                    value = nombre,
                    onValueChange = {
                        registroViewModel.onRegistroChanged(
                            it,
                            email,
                            telefono,
                            direccion,
                            password
                        )
                    },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = email,
                    onValueChange = {
                        registroViewModel.onRegistroChanged(
                            nombre,
                            it,
                            telefono,
                            direccion,
                            password
                        )
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = telefono,
                    onValueChange = {
                        registroViewModel.onRegistroChanged(
                            nombre,
                            email,
                            it,
                            direccion,
                            password
                        )
                    },
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = direccion,
                    onValueChange = {
                        registroViewModel.onRegistroChanged(
                            nombre,
                            email,
                            telefono,
                            it,
                            password
                        )
                    },
                    label = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password,
                    onValueChange = {
                        registroViewModel.onRegistroChanged(
                            nombre,
                            email,
                            telefono,
                            direccion,
                            it
                        )
                    },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Hide password" else "Show password"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { registroViewModel.registrarUsuario() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp)) // Aplica recorte con esquinas redondeadas
                        .background(Color(0xFFC12B2A)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent // Fondo del botón transparente
                    )
                ) { Text("Registrarse") }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { navController.navigate(AppRoutes.loginScreen.path) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp)) // Aplica recorte con esquinas redondeadas
                        .background(Color(0xFFC12B2A)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent // Fondo del botón transparente
                    )
                ) { Text("Regresar a Login") }

                // Mostrar el mensaje de error o éxito
                LaunchedEffect(registroResponse) {
                    registroResponse?.getContentNotChange()?.let { response ->
                        errorMessage = response.mensaje
                    }
                }
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = if (errorMessage.contains("Campos incompletos")) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}


