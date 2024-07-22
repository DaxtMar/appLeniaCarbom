package com.example.applenia_carbon.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.applenia_carbon.R
import androidx.compose.ui.text.style.TextAlign
import com.example.applenia_carbon.screens.tienda.carousel


val imageId = arrayOf(
    R.drawable.pro1,
    R.drawable.pro2,
    R.drawable.pro3,
    R.drawable.pro4,
)

val names = arrayOf(
    "Pollo + 1/4 pollo",
    "Pollo + 1/4 pollo + 1/2 tequeños",
    "Pollo + 1/4 pollo + bebida natural 1.5l",
    "Pollo + 1/4 pollo + bebida natural 1.5l + 1/2 tequeños",
)

val descripciones = arrayOf(
    "Un pollo a la brasa entero jugoso + 1/4 de pollo a la brasa + cocantes papas fritas",
    "Un pollo a la brasa entero jugoso + 1/4 de pollo a la brasa + cocantes papas fritas",
    "Un pollo a la brasa entero jugoso + 1/4 de pollo a la brasa + cocantes papas fritas",
    "Un pollo a la brasa entero jugoso + 1/4 de pollo a la brasa + cocantes papas fritas",
)

val precios = arrayOf(
    "80.90",
    "82.90",
    "90.90",
    "85.90",
)

@Composable
fun TiendaScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        MainScreen(imageId, names, descripciones, precios)
    }
}



@Composable
fun MainScreen(
    imageId: Array<Int>,
    names: Array<String>,
    descipciones: Array<String>,
    precios: Array<String>,
    modifier: Modifier = Modifier
) {

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        item(content = {
            carousel()
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = "Promociones",
                fontSize = 20.sp,
                fontWeight = Bold,
                textAlign = TextAlign.Start
            )
        })
        val itemCount = imageId.size
        items(itemCount) {
            ColumnItem(
                modifier,
                painter = imageId,
                title = names,
                descripciones = descipciones,
                precios = precios,
                itemIndex = it
            )
        }
    }
}

@Composable
fun ColumnItem(
    modifier: Modifier,
    painter: Array<Int>,
    title: Array<String>,
    descripciones: Array<String>,
    precios: Array<String>,
    itemIndex: Int,
) {
    Card(
        modifier
            .padding(10.dp)
            .wrapContentSize()
            .clickable {/**/ },
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
                painter = painterResource(id = painter[itemIndex]),
                contentDescription = title[itemIndex],
                modifier = Modifier
                    .height(200.dp)
                    .width(150.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier.padding(12.dp)) {
                Text(text = title[itemIndex], fontSize = 20.sp, fontWeight = Bold)
                Text(text = descripciones[itemIndex], fontSize = 16.sp)
                Text(text = precios[itemIndex], fontSize = 18.sp, fontWeight = Bold)
            }
        }
    }
}