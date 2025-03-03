package com.example.pants.domain.repository

import com.example.pants.domain.models.ColorModel

interface ColorRepository {

    suspend fun getRandomColors(count: Int): Result<Set<ColorModel>>
}
