package com.example.applenia_carbon.auth.domain

import com.example.applenia_carbon.auth.data.network.request.RegistroRequest
import com.example.applenia_carbon.auth.data.network.response.RegistroResponse
import com.example.applenia_carbon.auth.data.repository.AuthRepository
import javax.inject.Inject

class RegistroUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(registroRequest: RegistroRequest): RegistroResponse {
        return repository.registro(registroRequest)
    }
}