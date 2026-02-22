package com.erservice.app.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse<T>(
    val data: List<T>,
    val total: Int,
    val limit: Int,
    val offset: Int
)