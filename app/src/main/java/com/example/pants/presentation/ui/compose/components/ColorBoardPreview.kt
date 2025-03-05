package com.example.pants.presentation.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.pants.domain.models.ColorModel
import kotlinx.collections.immutable.PersistentList

@Composable
internal fun ColorBoardPreview(
    modifier: Modifier = Modifier,
    colors: PersistentList<ColorModel>,
) {
    Box(
        modifier = modifier
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            colors.forEach { color ->
                key(color.name) {
                    BorderedBox(color)
                }
            }
        }
    }
}

@Composable
private fun BorderedBox(color: ColorModel) {
    val infillColor = remember(color) { color.asComposeColor() }
    val outlineColor = remember(infillColor) { darkenColor(infillColor) }
    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(outlineColor)
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(infillColor)
        )
    }
}

private fun darkenColor(color: Color): Color {
    return Color(ColorUtils.blendARGB(color.toArgb(), Color.Black.toArgb(), 0.5f))
}

private fun ColorModel.asComposeColor(): Color {
    return guessHue?.let { hue -> Color.hsv(hue, 1f, 1f) } ?: Color.Gray
}
