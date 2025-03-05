package com.example.pants.presentation.ui.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pants.domain.models.ColorModel
import com.example.pants.presentation.ui.compose.animatedGradientTransition
import com.example.pants.presentation.extentions.hue
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun PickerContent(
    selectedColor: Color,
    onHueChange: (Float) -> Unit,
    colors: PersistentList<ColorModel>,
) {
    val (animatedColor, animatedGradient) = animatedGradientTransition(selectedColor)
    val stableColor = remember(animatedColor) { animatedColor }
    val stableGradient = remember(animatedGradient) { animatedGradient }
    Column(
        modifier = Modifier.width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(48.dp),
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ColorBoardPreview(modifier = Modifier.fillMaxHeight(), colors = colors)

            ColorPreview(
                color = selectedColor,
                animatedColor = stableColor,
                animatedGradient = stableGradient
            )
        }
        HuePicker(hue = selectedColor.hue, onHueChange = onHueChange)
    }
}

@Preview
@Composable
fun PickerContentPreview() {
    val model = ColorModel(
        name = "Color of your pants on fire on saturday morning",
        realHue = 227.0f,
        saturation = 1.0f,
        value = 1.0f,
        guessHue = null,
    )

    PickerContent(
        selectedColor = Color.Yellow,
        onHueChange = { _ -> },
        colors = List(5) { model }.toPersistentList()
    )
}
