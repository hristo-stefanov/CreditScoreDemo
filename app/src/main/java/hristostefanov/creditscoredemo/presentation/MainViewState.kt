package hristostefanov.creditscoredemo.presentation

import androidx.compose.runtime.Immutable

@Immutable
sealed interface MainViewState {
    @Immutable
    data class Success(
        val scoreText: String = "",
        val caption: String = "",
        val progress: Float = 0f
    ): MainViewState

    @Immutable
    data class Failure(val message: String): MainViewState

    @Immutable
    object Loading: MainViewState
}

