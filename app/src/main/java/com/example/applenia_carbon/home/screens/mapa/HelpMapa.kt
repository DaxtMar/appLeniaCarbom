package com.example.applenia_carbon.home.screens.mapa

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

fun createSizedMarker(
    mapView: MapView,
    context: Context,
    positionIn: GeoPoint,
    bitmap: Bitmap,
    texto:String
): Marker {
    //redimensiona el Bitmap
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
    val drawable = BitmapDrawable(context.resources, resizedBitmap)

    return Marker(mapView).apply {
        position = positionIn
        title = texto
        icon = drawable
        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        showInfoWindow()
    }
}

fun obtenerCoordenadas(jsonString: String): List<GeoPoint> {
    val type = object : TypeToken<List<List<Double>>>() {}.type

    //usamos Gson para deserializar el JSON
    val coordinates: List<List<Double>> = Gson().fromJson(jsonString, type)

    //convierte las coordenadas en GeoPoints
    return coordinates.map { coord ->
        val longitude = coord[0]
        val latitude = coord[1]
        GeoPoint(latitude, longitude)
    }
}