package com.example.pants.data.repositoryImpl

import com.example.pants.domain.models.ColorModel
import com.example.pants.data.network.ColorApiService
import com.example.pants.domain.repository.ColorRepository
import com.example.pants.domain.utils.generateRandomColor
import com.example.pants.data.mapper.toColorModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.Locale

class ColorRepositoryImpl(
    private val apiService: ColorApiService,
) : ColorRepository {

    override suspend fun getRandomColors(count: Int): Result<Set<ColorModel>> = runCatching {
        val uniqueColors = mutableSetOf<ColorModel>()

        while (uniqueColors.size < count) {
            val requestsToMake = count - uniqueColors.size
            val newColors = coroutineScope {
                (1..requestsToMake).map {
                    async { apiService.getColor(generateRandomColor()).toColorModel() }
                }.awaitAll()
            }
            newColors.forEach { color ->
                if (isValidColor(color)) {
                    uniqueColors.add(color)
                }
            }
        }
        uniqueColors
    }

    private fun isValidColor(color: ColorModel): Boolean {
        return color.name.lowercase(Locale.getDefault()) !in COMMON_USE_NAMES
    }

    private companion object {
        val COMMON_USE_NAMES = setOf(
            "beige",
            "black",
            "blue violet",
            "blue",
            "brown",
            "crimson",
            "cyan",
            "gold",
            "gray",
            "green",
            "indigo",
            "khaki",
            "lavender",
            "lime green",
            "magenta",
            "maroon",
            "navy blue",
            "olive",
            "orange",
            "pink",
            "plum",
            "purple",
            "red",
            "salmon",
            "silver",
            "sky blue",
            "teal",
            "violet",
            "white",
            "yellow",
        )
    }
}