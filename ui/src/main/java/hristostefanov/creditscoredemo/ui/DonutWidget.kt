package hristostefanov.creditscoredemo.ui.donut

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

private val BORDER_WIDTH = 1.dp
private val PROGRESS_INDICATOR_STROKE_WIDTH = 4.dp
private val MARGIN_INSIDE_PROGRESS_INDICATOR = 16.dp
private val MARGIN_INSIDE_BORDER = 4.dp

@Composable
fun DonutWidget(progress: Float, scoreText: String, caption: String, title: String) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .border(BORDER_WIDTH, MaterialTheme.colors.onBackground, CircleShape)
            .padding(BORDER_WIDTH + MARGIN_INSIDE_BORDER)
    ) {
        CircularProgressIndicator(
            progress = progress,
            modifier = Modifier
                .matchParentSize()
                .testTag("Progress indicator"),
            color = MaterialTheme.colors.secondary,
            strokeWidth = PROGRESS_INDICATOR_STROKE_WIDTH
        )
        Column(
            modifier = Modifier
                .padding(MARGIN_INSIDE_PROGRESS_INDICATOR + PROGRESS_INDICATOR_STROKE_WIDTH)
                .layout(circleFitContentLayout),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                title,
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                scoreText,
                style = MaterialTheme.typography.h2,
                fontWeight = FontWeight.W200,
                color = MaterialTheme.colors.secondary
            )
            Text(
                caption,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

private val circleFitContentLayout =
    fun MeasureScope.(measurable: Measurable, constraints: Constraints): MeasureResult {
        val placeable = measurable.measure(constraints)

        // the diagonal of the content rectangle
        val diagonal =
            sqrt(placeable.width.toDouble().pow(2.0) + placeable.height.toDouble().pow(2.0))

        // diameter of the circle in which the content rectangle is fit
        val diameter = ceil(diagonal).toInt()

        return layout(diagonal.roundToInt(), diagonal.roundToInt()) {
            // center the content
            val x = (diameter - placeable.width) / 2
            val y = (diameter - placeable.height) / 2
            placeable.placeRelative(x, y)
        }
    }
