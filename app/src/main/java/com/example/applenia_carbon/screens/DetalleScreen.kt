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
            //elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "atras",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ProductDetails(producto: ProductoResponse) {
    Column {
        // Título del producto
        Text(
            text = producto.nombre, fontSize = 18.sp, fontWeight = Bold
            //style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(4.dp))
        /*
        Row(verticalAlignment = Alignment.CenterVertically) {
            RatingBar(rating = 4.5)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "(2709)", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$1099",
                //style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$1234",
                //style = MaterialTheme.typography.body2,
                textDecoration = TextDecoration.LineThrough,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "-11%",
                color = Color.Green,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))*/

        Text(
            text = producto.descripcion,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "S/"+producto.precio.toString(), fontSize = 16.sp, fontWeight = Bold
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
            hostState = snackbarHostState,
            //modifier = Modifier.align(Alignment.BottomEnd)
        )
//        Button(
//            onClick = { },
//            //colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
//        ) {
//            Text(text = "Ver Carrito", color = Color.White)
//        }
    }
}

@Composable
fun RatingBar(rating: Double) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (index < rating) Color.Yellow else Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

/*
@Composable
fun DetalleScreen(
    /*modifier: Modifier = Modifier,*/
    id: Int,
    navController: NavController,
    cartViewModel: CartViewModel,
    homeViewModel: HomeViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val productos by homeViewModel.productoResponse.observeAsState(emptyList())
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)//alrededor
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)//espacio dentro de los componentes
    ) {

        Box(
            //modifier.fillMaxWidth(),
            Modifier.fillMaxWidth(),
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
                Modifier.clip(RoundedCornerShape(16.dp))
                .height(180.dp)
                .width(200.dp),
                contentScale = ContentScale.Crop
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
}*/