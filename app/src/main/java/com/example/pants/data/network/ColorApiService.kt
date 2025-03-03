package com.example.pants.data.network

import com.example.pants.domain.models.ColorResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ColorApiService {
    @GET("id")
    suspend fun getColor(
        @Query("hsv") hsv: String
    ): ColorResponse
}
