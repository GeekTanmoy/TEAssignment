package com.example.techexactly.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("apps/list")
    suspend fun appList(
        @Query("kid_id") kidId: String
    ): Response<ApiResponse>
}