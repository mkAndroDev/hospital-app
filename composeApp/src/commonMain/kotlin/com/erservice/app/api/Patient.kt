package com.erservice.app.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Patient(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val pesel: String,
    val condition: String,
    @SerialName("admittedAt")
    val createdAt: String,
    val status: String
)