package hristostefanov.creditscoredemo.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import hristostefanov.creditscoredemo.presentation.MainViewState
import hristostefanov.creditscoredemo.ui.theme.CreditScoreDemoTheme
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

private val BORDER_WIDTH = 20.dp
private val PROGRESS_INDICATOR_STROKE_WIDTH = 10.dp
private val MARGIN_INSIDE_PROGRESS_INDICATOR = 3.dp
private val MARGIN_INSIDE_BORDER = 10.dp

@Composable
fun MainScreen(viewState: MainViewState) {
    Scaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CreditScoreWidget(viewState)
        }
    }
}

val circleFitContentLayout =
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


@Composable
fun CreditScoreWidget(viewState: MainViewState) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .border(BORDER_WIDTH, Color.Companion.Red, CircleShape)
            .padding(BORDER_WIDTH + MARGIN_INSIDE_BORDER)
    ) {
        CircularProgressIndicator(
            progress = 0.5f,
            modifier = Modifier.matchParentSize(),
            color = Color.Blue,
            strokeWidth = PROGRESS_INDICATOR_STROKE_WIDTH
        )
        Column(
            modifier = Modifier
                .padding(MARGIN_INSIDE_PROGRESS_INDICATOR + PROGRESS_INDICATOR_STROKE_WIDTH)
                .layout(circleFitContentLayout),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Your credit score is")
            Text(viewState.scoreText)
            Text(viewState.caption)
        }
    }
}

@Preview
@Composable
fun Preview() {
    CreditScoreDemoTheme {
        MainScreen(
            viewState = MainViewState(
                scoreText = "327",
                caption = "out of 700"
            )
        )
    }
}