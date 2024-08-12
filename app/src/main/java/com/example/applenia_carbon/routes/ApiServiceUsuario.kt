package com.example.applenia_carbon.routes

import com.example.applenia_carbon.Models.LoginRequest
import com.example.applenia_carbon.Models.LoginResponse
import com.example.applenia_carbon.Models.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceUsuario {

    @GET("usuarios/{id}")
    fun getUsuarioById(@Path("id") id: Int): Call<Usuario>

    @POST("usuarios")
    fun createUsuario(@Body usuario: Usuario): Call<Usuario>

    @PUT("usuarios/{id}")
    fun updateUsuario(@Path("id") id: Int, @Body usuario: Usuario): Call<Usuario>

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

}