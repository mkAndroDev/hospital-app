package com.erservice.app.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class AdmitPatientRequest(
    val firstName: String,
    val lastName: String,
    val pesel: String,
    val condition: String
)