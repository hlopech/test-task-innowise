package com.example.pants.presentation.ui.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pants.domain.models.ColorModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun Previews(
    modifier: Modifier = Modifier,
    colors: PersistentList<ColorModel>,
    selectedColor: Color,
    animatedColor: Color,
    animatedGradient: Brush,
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ColorBoardPreview(modifier = Modifier.fillMaxHeight(), colors = colors)

        ColorPreview(
            color = selectedColor,
        )
    }
}

@Preview
@Composable
fun PreviewsPreview() {
    val model = ColorModel(
        name = "Color of your pants on fire on saturday morning",
        realHue = 227.0f,
        saturation = 1.0f,
        value = 1.0f,
        guessHue = null,
    )

    Previews(
        modifier = Modifier,
        selectedColor = Color.Blue,
        colors = List(5) { model }.toPersistentList(),
        animatedColor = Color.Yellow,
        animatedGradient = Brush.linearGradient(
            0f to Color.Yellow,
            0.5f to Color.Green,
            1f to Color.Blue
        ),
    )
}
