package com.example.applenia_carbon.home.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.applenia_carbon.R
import com.example.applenia_carbon.home.data.network.response.CategoriaResponse
import com.example.applenia_carbon.home.viewmodel.HomeViewModel
import com.example.applenia_carbon.core.routes.AppRoutes


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
    val categorias by homeViewModel.categoriaResponse.observeAsState(emptyList())
    var searchText by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        homeViewModel.listarCategorias()
    }
    val filterCategorias = categorias.filter { it.nombre.contains(searchText, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Buscar") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar",
                    tint = Color.Gray
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = modifier
        ) {
            items(filterCategorias.size) { indice ->
                val categoria = filterCategorias[indice]
                CategoryCard(categoria, navController = navController)
            }
        }
    }
}


@Composable
fun CategoryCard(
    categoriaResponse: CategoriaResponse,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Surface(
        color = Color.Transparent,
        shape = MaterialTheme.shapes.extraLarge,
        shadowElevation = 6.dp,
        modifier = modifier
            .size(160.dp)
            .clickable {
                navController.navigate(AppRoutes.tiendaScreen.createRoute(categoriaResponse.id))
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
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)

            )
        }
    }
}
