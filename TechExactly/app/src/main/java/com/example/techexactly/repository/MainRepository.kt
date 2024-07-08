package com.example.techexactly.repository

import com.example.techexactly.api.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun appList(kidId: String) = apiService.appList(kidId)
}