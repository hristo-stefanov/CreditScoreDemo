package hristostefanov.creditscoredemo.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import hristostefanov.creditscoredemo.R
import hristostefanov.creditscoredemo.presentation.MainViewState
import hristostefanov.creditscoredemo.ui.theme.CreditScoreDemoTheme
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

private val BORDER_WIDTH = 1.dp
private val PROGRESS_INDICATOR_STROKE_WIDTH = 4.dp
private val MARGIN_INSIDE_PROGRESS_INDICATOR = 16.dp
private val MARGIN_INSIDE_BORDER = 4.dp

@Composable
fun MainScreen(viewState: MainViewState, onRetry: () -> Unit) {
    Scaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (viewState) {
                is MainViewState.Success -> {
                    DonutWidget(
                        progress = viewState.progress,
                        scoreText = viewState.scoreText,
                        caption = viewState.caption
                    )
                }
                is MainViewState.Failure -> {
                    Failure(viewState.message, onRetry)
                }
                is MainViewState.Loading -> {
                    Loading()
                }
                is MainViewState.Empty -> {
                    // empty
                }
            }
        }
    }
}

@Composable
fun Loading() {
    CircularProgressIndicator()
}

@Composable
fun Failure(message: String, onRetry: () -> Unit) {
    Column(
        Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message, textAlign = TextAlign.Center, color = MaterialTheme.colors.error)
        Spacer(Modifier.size(16.dp))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
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


@Composable
fun DonutWidget(progress: Float, scoreText: String, caption: String) {
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
                stringResource(R.string.yourCreditScoreIs),
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

@Preview
@Composable
fun PreviewLoading() {
    CreditScoreDemoTheme {
        Loading()
    }
}

@Preview
@Composable
fun PreviewFailure() {
    CreditScoreDemoTheme {
        Failure("An error occured", {})
    }
}

@Preview
@Composable
fun PreviewSuccess() {
    CreditScoreDemoTheme {
        MainScreen(
            viewState = MainViewState.Success(
                scoreText = "327",
                caption = "out of 700",
                progress = 0.5f
            )
        ) {}
    }
}