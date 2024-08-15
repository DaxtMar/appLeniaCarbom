package com.example.applenia_carbon.Retrofit

import com.example.applenia_carbon.dataEjemplo.ipserver
//import com.example.applenia_carbon.routes.ApiServiceUsuario
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.35:8091/"  // Utiliza tu IP

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ipserver)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiServiceUsuario: ApiServiceUsuario by lazy {
        retrofit.create(ApiServiceUsuario::class.java)
    }
}*/