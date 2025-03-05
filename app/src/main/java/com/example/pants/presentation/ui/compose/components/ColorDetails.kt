package com.example.pants.presentation.ui.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pants.R
import com.example.pants.presentation.extentions.hue

@Composable
internal fun ColorDetails(modifier: Modifier, color: Color) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        DataPointPresenter("HUE", stringResource(R.string.hue_data, color.hue))
        DataPointPresenter(
            "RGB",
            String.format(stringResource(R.string.rgb_data), color.red, color.green, color.blue)
        )
    }
}

@Composable
internal fun DataPointPresenter(title: String, data: String) {
    Column(
        modifier = Modifier
            .semantics(mergeDescendants = true) {
                contentDescription = "$title: $data"
                isTraversalGroup = true
            }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            modifier = Modifier.clearAndSetSemantics { })
        Text(
            text = data,
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.clearAndSetSemantics { })
    }
}