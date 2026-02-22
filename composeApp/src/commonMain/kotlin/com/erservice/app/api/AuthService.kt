package com.erservice.app.api

import com.erservice.app.api.dto.LoginRequest
import com.erservice.app.api.dto.LoginResponse
import com.erservice.app.api.dto.RegisterRequest
import User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

interface AuthService {
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun register(request: RegisterRequest)
    suspend fun me(): User
}

class AuthServiceImpl(private val httpClient: HttpClient) : AuthService {

    override suspend fun login(request: LoginRequest): LoginResponse {
        return httpClient.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun register(request: RegisterRequest) {
        httpClient.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun me(): User {
        return httpClient.get("/auth/me").body()
    }
}