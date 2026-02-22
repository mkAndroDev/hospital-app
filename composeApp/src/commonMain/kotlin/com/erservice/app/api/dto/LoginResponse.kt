package com.erservice.app.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(val token: String)