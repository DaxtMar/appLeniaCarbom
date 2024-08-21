package com.example.applenia_carbon.auth.data.repository

import com.example.applenia_carbon.auth.data.network.request.LoginRequest
import com.example.applenia_carbon.auth.data.network.request.RegistroRequest
import com.example.applenia_carbon.auth.data.network.response.LoginResponse
import com.example.applenia_carbon.auth.data.network.response.RegistroResponse
import com.example.applenia_carbon.auth.data.network.service.AuthService
import javax.inject.Inject

class AuthRepository @Inject
    constructor(private val authService: AuthService) {

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return authService.login(loginRequest)
    }

    suspend fun registro(registroRequest: RegistroRequest): RegistroResponse{
        return authService.registro(registroRequest)
    }

}