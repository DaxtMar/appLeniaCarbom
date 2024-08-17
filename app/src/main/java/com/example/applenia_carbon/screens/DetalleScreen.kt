package com.example.applenia_carbon.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.applenia_carbon.R
import com.example.applenia_carbon.home.data.network.response.ProductoResponse
import com.example.applenia_carbon.home.viewmodel.HomeViewModel
import com.example.applenia_carbon.screens.viewmodel.CartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DetalleScreen(
    id: Int,
    navController: NavController,
    cartViewModel: CartViewModel,
    homeViewModel: HomeViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val productos by homeViewModel.productoResponse.observeAsState(emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        ProductImage(productos.get(id).imagen, navController)

        Spacer(modifier = Modifier.height(8.dp))

        ProductDetails(productos.get(id))

        Spacer(modifier = Modifier.height(16.dp))

        ActionButtons(productos.get(id), snackbarHostState, coroutineScope, cartViewModel)
    }
}

@Composable
fun ProductImage(imageUrl: String, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(310.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = imageUrl)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                        placeholder(R.drawable.lenaycarbon)
                    }).build()
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = {
                navController.popBackStack()
            },
            Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .background(Color.Black.copy(alpha = 0.6f), shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "atras",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ProductDetails(producto: ProductoResponse) {
    Column {

        Text(
            text = producto.nombre, fontSize = 18.sp, fontWeight = Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = producto.descripcion,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "S/" + producto.precio.toString(), fontSize = 16.sp, fontWeight = Bold
        )
    }
}

@Composable
fun ActionButtons(
    producto: ProductoResponse,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    cartViewModel: CartViewModel,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Button(
            onClick = {
                cartViewModel.addProductToCart(producto)
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Agregado",
                        actionLabel = "OK", duration = SnackbarDuration.Short
                    )
                }
            },
            modifier
            = Modifier.fillMaxWidth()
        ) {
            Text("Agregar al carrito")
        }
        SnackbarHost(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            hostState = snackbarHostState
        )
    }
}
