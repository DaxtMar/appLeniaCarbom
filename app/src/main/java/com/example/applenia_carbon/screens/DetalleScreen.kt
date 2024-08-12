package com.example.applenia_carbon.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.applenia_carbon.R
import com.example.applenia_carbon.dataEjemplo.Producto
import com.example.applenia_carbon.dataEjemplo.listaProductos
import com.example.applenia_carbon.home.data.network.response.ProductoResponse
import com.example.applenia_carbon.home.viewmodel.HomeViewModel
import com.example.applenia_carbon.screens.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@Composable
fun DetalleScreen(
    modifier: Modifier = Modifier,
    id: Int,
    navController: NavController,
    cartViewModel: CartViewModel,
    homeViewModel: HomeViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val productos by homeViewModel.productoResponse.observeAsState(emptyList())
    Column(
        modifier
            .fillMaxSize()
            .padding(20.dp)//alrededor
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)//espacio dentro de los componentes
    ) {

        Box(
            //modifier.fillMaxWidth(),
            modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                //painter = painterResource(id = listaProductos.get(id).image),
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = productos.get(id).imagen)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            placeholder(R.drawable.lenaycarbon)
                        }).build()
                ),
                //painter = painterResource(id = R.drawable.lenaycarbon),
                contentDescription = productos.get(id).nombre,
                modifier.clip(RoundedCornerShape(16.dp))
            )
            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.TopStart),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent
                ),
                //elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Favorite Icon",
                    tint = Color.White
                )
            }
        }
        //Text(text = listaProductos.get(id).title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = productos.get(id).nombre, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = productos.get(id).descripcion, fontSize = 16.sp)

        Text(text = productos.get(id).precio.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        val producto: ProductoResponse = productos.get(id)
        Spacer(modifier = Modifier.height(6.dp))
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
            hostState = snackbarHostState,
            //modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}