package com.example.applenia_carbon.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.applenia_carbon.R
import com.example.applenia_carbon.dataEjemplo.dataCategoria
import com.example.applenia_carbon.dataEjemplo.listaCategorias
import com.example.applenia_carbon.home.data.network.response.CategoriaResponse
//import com.example.applenia_carbon.home.viewmodel.CategoriaViewModel
import com.example.applenia_carbon.home.viewmodel.HomeViewModel
import com.example.applenia_carbon.routes.AppRoutes


@Composable
fun categoriaScreen(navController: NavController, homeViewModel: HomeViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CategoryScreen(navController = navController, homeViewModel = homeViewModel)
    }
}

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    //val viewModel: CategoriaViewModel = viewModel()
    //val categorias by viewModel.categorias.observeAsState(emptyList())
    val categorias by homeViewModel.categoriaResponse.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        homeViewModel.listarCategorias()
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        items(categorias) { category ->
            CategoryCard(category, navController = navController)
        }
        /*
        items(listaCategorias) { category ->
            CategoryCard(category, navController = navController)
        }*/
    }
}

@Composable
fun CategoryCard(
    /*categoria: dataCategoria,*/
    categoriaResponse: CategoriaResponse,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    //val image: Painter = painterResource(id = categoria.imageResId)
    Surface(
        color = Color.Transparent,
        shape = MaterialTheme.shapes.extraLarge,
        shadowElevation = 6.dp,
        modifier = modifier
            .size(160.dp)
            .clickable {
                // navController.navigate(AppRoutes.tiendaScreen.createRoute(categoria.idc))
            },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = categoriaResponse.imagen)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            placeholder(R.drawable.lenaycarbon)
                        }).build()
                ), contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)) // ajustar opacidad
            )
            Text(
                text = categoriaResponse.nombre,
                style = TextStyle(color = Color.White, fontSize = 20.sp),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
