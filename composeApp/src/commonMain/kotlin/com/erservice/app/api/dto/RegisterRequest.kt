package com.erservice.app.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username: String,
    val password: String,
    val fullName: String,
    val role: String
)