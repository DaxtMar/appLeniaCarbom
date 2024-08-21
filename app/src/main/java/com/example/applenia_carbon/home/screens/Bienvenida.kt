package com.example.applenia_carbon.home.screens

import android.os.Handler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.applenia_carbon.R
import com.example.applenia_carbon.core.routes.AppRoutes

@Composable
fun WelcomeScreen(navController: NavController) {
    var imageOffset by remember { mutableStateOf(-300.dp) }
    var textOffset by remember { mutableStateOf(300.dp) }

    val animatedImageOffset by animateDpAsState(
        targetValue = imageOffset,
        animationSpec = tween(durationMillis = 1000) // Duración de la animación
    )

    val animatedTextOffset by animateDpAsState(
        targetValue = textOffset,
        animationSpec = tween(durationMillis = 1000) // Duración de la animación
    )

    LaunchedEffect(Unit) {
        // Inicializar la animación al cargar la pantalla
        imageOffset = 0.dp
        textOffset = 0.dp

        // Usar Handler para navegar después de un tiempo
        Handler().postDelayed({
            navController.navigate(AppRoutes.loginScreen.path) {
                // Evita que el usuario vuelva a la pantalla de bienvenida
                popUpTo(AppRoutes.loginScreen.path) { inclusive = true }
            }
        }, 2000) // Tiempo en milisegundos (2000ms = 2 segundos)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Imagen animada
        Image(
            painter = painterResource(id = R.drawable.lenaycarbon), // Tu imagen aquí
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .offset(y = animatedImageOffset) // Aplicar animación de desplazamiento
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Texto animado
        Text(
            text = "Tú mejor opción en comidas",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.offset(y = animatedTextOffset) // Aplicar animación de desplazamiento
        )
    }
}