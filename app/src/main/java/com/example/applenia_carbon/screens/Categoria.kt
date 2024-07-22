package com.example.applenia_carbon.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import com.example.applenia_carbon.R
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class dataCategoria(val imageResId: Int, val name: String)

val listaCategorias = listOf(
    dataCategoria(R.drawable.pro1, "Promociones"),
    dataCategoria(R.drawable.fc1, "Festival de cuartos"),
    dataCategoria(R.drawable.pb1, "pollo a la brasa"),
    dataCategoria(R.drawable.cp1, "carnes parrillas"),
    dataCategoria(R.drawable.cl1, "Complementos"),
    dataCategoria(R.drawable.ep1, "Entradas y piqueos"),
    dataCategoria(R.drawable.pc1, "Postres"),
    dataCategoria(R.drawable.bb1, "Bebidas"),
)

@Composable
fun CategoriaScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        buscar()
        Spacer(Modifier.size(15.dp))
        CategoriaGrid(listaCategorias)
    }
}


@Composable
fun CategoriaGrid(dataCategorias: List<dataCategoria>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(dataCategorias) { place ->
            CategoriaItem(place)
        }
    }
}

@Composable
fun buscar() {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Search") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "buscar") },

        )
}

@Composable
fun CategoriaItem(dataCategoria: dataCategoria) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /**/ },
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = dataCategoria.imageResId),
            contentDescription = dataCategoria.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = dataCategoria.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}