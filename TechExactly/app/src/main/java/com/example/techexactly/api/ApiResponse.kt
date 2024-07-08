package com.example.techexactly.api

import com.example.techexactly.models.Data

data class ApiResponse(
    val success: Boolean,
    val data: Data,
    val message: String
)
