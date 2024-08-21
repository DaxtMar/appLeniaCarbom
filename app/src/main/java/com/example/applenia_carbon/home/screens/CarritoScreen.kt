package com.example.applenia_carbon.home.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.applenia_carbon.R
import com.example.applenia_carbon.core.routes.AppRoutes
import com.example.applenia_carbon.home.screens.modelo.CartItem
import com.example.applenia_carbon.home.screens.viewmodel.CartViewModel

@Composable
fun carritoScreen(cartViewModel: CartViewModel, navController: NavController) {

    val cartItems = cartViewModel.cartItems
    Column(modifier = Modifier.padding(10.dp)) {

        if (cartItems.isEmpty()) {
            EmptyCartView()
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(cartItems) { cartItem ->
                    CartItemRow(cartItem,
                        onIncrement = {
                            cartViewModel.addProductToCart(cartItem.producto)
                        },
                        onDecrement = {
                            cartViewModel.removeProductFromCart(cartItem.producto)
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate(AppRoutes.pasarelaScreen.path)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC12B2A),
                    contentColor = Color.White
                ),
            ) {
                Text("Ir a Pagar")
            }
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFC12B2A)),
        colors = CardDefaults.cardColors(
            //contentColor = Color.White,
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = cartItem.producto.imagen)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            placeholder(R.drawable.lenaycarbon)
                        }).build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = cartItem.producto.nombre)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "\$${cartItem.producto.precio}", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Row(
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDecrement,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFC12B2A), shape = RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = "Decremento",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${cartItem.cantidad}",
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = onIncrement,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFC12B2A), shape = RoundedCornerShape(8.dp))
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Incremento", tint = Color.White)
                }
            }
        }
    }
}

@Composable
fun EmptyCartView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tu Carrito esta vacio",
            color = Color.Gray,
            fontSize = 20.sp,
        )
    }
}