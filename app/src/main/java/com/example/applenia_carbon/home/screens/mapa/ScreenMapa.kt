package com.example.applenia_carbon.home.screens.mapa

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.applenia_carbon.R
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline

@Composable
fun MapaConRuta(texto: String, navController: NavController) {

    val coordenadas: List<GeoPoint> = obtenerCoordenadas(texto)
    val context = LocalContext.current
    LaunchedEffect(context) {
        org.osmdroid.config.Configuration.getInstance()
            .load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
    }

    val mapView = remember {

        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            controller.setZoom(16.0)
            controller.setCenter(coordenadas.first())
            zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)

            // Agregar la línea de la ruta
            val polyline = Polyline().apply {
                setPoints(coordenadas)
                color = android.graphics.Color.RED
                width = 8f
            }

            overlayManager.add(polyline)
            val bitmapOrigen: Bitmap =
                BitmapFactory.decodeResource(context.resources, R.drawable.lenaycarbon)
            val bitmapDestino: Bitmap =
                BitmapFactory.decodeResource(context.resources, R.drawable.casa)
            val markerdestino = createSizedMarker(
                this,
                context,
                coordenadas.get(coordenadas.size - 1),
                bitmapDestino,
                "destino"
            )
            overlayManager.add(markerdestino)
            // Agregar el marcador si se ha proporcionado una posición
            val markerOrigen = createSizedMarker(
                this,
                context,
                coordenadas.first(),
                bitmapOrigen, "Leña y Carbon"
            )
            overlayManager.add(markerOrigen)
            invalidate() // Redibuja el mapa para mostrar las actualizaciones
        }
    }

    Box {
        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            IconButton(
                onClick = { mapView.controller.zoomIn() },
                modifier = Modifier
                    .size(26.dp)
                    .background(Color(0xFFC12B2A), shape = RoundedCornerShape(8.dp))
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "zoomIn",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            IconButton(
                onClick = { mapView.controller.zoomOut() },
                modifier = Modifier
                    .size(26.dp)
                    .background(Color(0xFFC12B2A), shape = RoundedCornerShape(8.dp))
            ) {
                Icon(
                    Icons.Default.Remove,
                    contentDescription = "zoomOut",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC12B2A),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Atras")
            }
        }
    }
}

