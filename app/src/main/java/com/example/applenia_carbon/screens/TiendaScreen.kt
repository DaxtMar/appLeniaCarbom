package com.example.applenia_carbon.screens


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.applenia_carbon.R
import com.example.applenia_carbon.dataEjemplo.Producto
import com.example.applenia_carbon.dataEjemplo.listaCategorias
import com.example.applenia_carbon.dataEjemplo.listaProductos

import com.example.applenia_carbon.routes.AppRoutes
import com.example.applenia_carbon.screens.tienda.carousel

@Composable
fun tiendaScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumnExample(navController = navController)
    }
}

@Composable
fun ColumnItem(
    modifier: Modifier,
    producto: Producto,
    navController: NavController
) {
    Card(
        modifier
            .padding(10.dp)
            .wrapContentSize()
            .clickable {
                navController.navigate(AppRoutes.detalleScreen.paramDetalle(producto.idp - 1))
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {

        Row(
            modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Image(
                painter = painterResource(id = producto.image),
                contentDescription = producto.title,
                modifier = Modifier
                    .height(200.dp)
                    .width(150.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier.padding(12.dp)) {
                Text(text = producto.title, fontSize = 20.sp, fontWeight = Bold)
                Text(text = producto.descripcion, fontSize = 16.sp)
                Text(text = producto.precio.toString(), fontSize = 18.sp, fontWeight = Bold)
                //Text(text = producto.idp.toString(), fontSize = 12.sp)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyColumnExample(modifier: Modifier = Modifier, navController: NavController) {
    Column {
        LazyColumn(
            Modifier.padding(start = 10.dp, end = 10.dp),
            flingBehavior = ScrollableDefaults.flingBehavior(),
            state = rememberLazyListState(),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                item(content = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.lenaycarbon),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(CircleShape)
                        )
                    }
                    carousel()
                    Spacer(Modifier.size(10.dp))
                })
                val agrupa = listaProductos.groupBy { (it.idp - 1) / 4 + 1 }
                agrupa.entries.forEach { entry ->
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Red)
                                .padding(6.dp)
                        ) {
                            Text(
                                text = "${listaCategorias[entry.key - 1].name}",
                                fontWeight = Bold,
                                color = Color.White
                            )
                        }
                    }
                    items(entry.value) { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                ),
                        ) {
                            Column(
                                modifier = Modifier.padding(6.dp)
                            ) {
                                ColumnItem(
                                    modifier,
                                    item,
                                    navController
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}