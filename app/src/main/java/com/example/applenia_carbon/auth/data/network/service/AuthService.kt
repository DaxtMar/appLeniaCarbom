package com.example.applenia_carbon.auth.data.network.service

import com.example.applenia_carbon.auth.data.network.request.LoginRequest
import com.example.applenia_carbon.auth.data.network.response.LoginResponse
import com.example.applenia_carbon.core.retrofit.LeniaCarbonClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthService @Inject constructor(
    private val leniaCarbonClient: LeniaCarbonClient
) {
    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return withContext(Dispatchers.IO) {
            val response = leniaCarbonClient.login(loginRequest)
            response.body()!!
        }
    }
}