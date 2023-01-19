package hristostefanov.creditscoredemo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hristostefanov.creditscoredemo.ui.donut.DonutWidget
import hristostefanov.creditscoredemo.donut.R
import hristostefanov.creditscoredemo.ui.theme.CreditScoreDemoTheme

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
                        caption = viewState.caption,
                        title = stringResource(id = R.string.yourCreditScoreIs)
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