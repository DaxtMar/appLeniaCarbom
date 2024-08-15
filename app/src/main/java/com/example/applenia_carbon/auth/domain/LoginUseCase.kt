package com.example.applenia_carbon.auth.domain

import com.example.applenia_carbon.auth.data.network.request.LoginRequest
import com.example.applenia_carbon.auth.data.network.response.LoginResponse
import com.example.applenia_carbon.auth.data.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(loginRequest: LoginRequest): LoginResponse {
        return repository.login(loginRequest)
    }
}