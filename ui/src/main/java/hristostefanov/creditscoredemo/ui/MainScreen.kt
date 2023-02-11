package hristostefanov.creditscoredemo.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hristostefanov.creditscoredemo.ui.theme.CreditScoreDemoTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    MainScreen(viewState, viewModel::onRetry, modifier)
}

@Composable
fun MainScreen(viewState: MainViewState, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(modifier) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
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
private fun Loading() {
    CircularProgressIndicator()
}

@Composable
private fun Failure(message: String, onRetry: () -> Unit) {
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

val MainScreenPreviewSuccess
    get() = MainViewState.Success(
        "350",
        "out of 700",
        0.5f
    )

val MainScreenPreviewFailure get() = MainViewState.Failure("An error occurred")

val MainScreenPreviewStates
    get() = listOf(
        MainScreenPreviewSuccess,
        MainViewState.Empty,
        MainViewState.Loading,
        MainScreenPreviewFailure
    )

private class StateProvider : PreviewParameterProvider<MainViewState> {
    override val values: Sequence<MainViewState> = MainScreenPreviewStates.asSequence()
}

@Preview("Default")
@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Large text", fontScale = 1.5f)
@Composable
private fun Preview(@PreviewParameter(StateProvider::class) viewState: MainViewState) {
    CreditScoreDemoTheme {
        MainScreen(viewState, {})
    }
}