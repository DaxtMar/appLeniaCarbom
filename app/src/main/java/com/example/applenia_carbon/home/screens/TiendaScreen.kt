package com.example.applenia_carbon.home.screens


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.applenia_carbon.R
import com.example.applenia_carbon.home.data.network.response.ProductoResponse
import com.example.applenia_carbon.home.viewmodel.HomeViewModel

import com.example.applenia_carbon.core.routes.AppRoutes
import com.example.applenia_carbon.home.screens.tienda.carousel

@Composable
fun tiendaScreen(navController: NavController, homeViewModel: HomeViewModel, categoriaIndex: Int?) {

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumnExample(
            navController = navController,
            categoryId = categoriaIndex,
            homeViewModel = homeViewModel
        )
    }
}

@Composable
fun ColumnItem(
    modifier: Modifier,
    productoResponse: ProductoResponse,
    navController: NavController
) {
    Card(
        modifier
            .padding(8.dp)
            .wrapContentSize()
            .clickable {
                navController.navigate(AppRoutes.detalleScreen.paramDetalle(productoResponse.idp - 1))
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Row(
            modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = productoResponse.imagen)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            placeholder(R.drawable.lenaycarbon)
                        }).build()
                ), contentDescription = null,
                modifier = Modifier
                    .height(140.dp)
                    .width(120.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier.padding(6.dp)) {
                Text(
                    text = productoResponse.nombre,
                    fontSize = 18.sp,
                    fontWeight = Bold,
                    maxLines = 1
                )
                Text(
                    text = productoResponse.descripcion, fontSize = 15.sp, maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = productoResponse.precio.toString(), fontSize = 16.sp, fontWeight = Bold)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyColumnExample(
    modifier: Modifier = Modifier,
    navController: NavController,
    categoryId: Int?,
    homeViewModel: HomeViewModel
) {
    val productos by homeViewModel.productoResponse.observeAsState(emptyList())
    val categorias by homeViewModel.categoriaResponse.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        homeViewModel.listarCategorias()
    }
    Column {
        val listState = rememberLazyListState()

        LaunchedEffect(categoryId) {
            categoryId?.let {
                if (categoryId >= 0) {
                    listState.animateScrollToItem(categoryId * 5)
                }
            }
        }
        //Text(text = "${categoryId} ")
        LazyColumn(
            Modifier.padding(start = 10.dp, end = 10.dp),
            flingBehavior = ScrollableDefaults.flingBehavior(),
            state = listState,
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

                categorias.forEach { categoria ->
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFC12B2A))
                                .padding(6.dp)
                        ) {
                            Text(
                                text = categoria.nombre,
                                fontWeight = Bold,
                                color = Color.White
                            )
                        }
                    }
                    items(productos.filter { it.idc == categoria.id }) { producto ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier.padding(6.dp)
                            ) {
                                ColumnItem(
                                    modifier,
                                    producto,
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