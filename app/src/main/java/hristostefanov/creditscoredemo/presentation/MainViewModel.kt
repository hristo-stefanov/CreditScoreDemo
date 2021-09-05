package hristostefanov.creditscoredemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hristostefanov.creditscoredemo.R
import hristostefanov.creditscoredemo.util.StringSupplier
import hristostefanov.creditscoredemo.business.DataAccessException
import hristostefanov.creditscoredemo.business.ReportCreditScoreProgressInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val reportCreditScoreProgressInteractor: ReportCreditScoreProgressInteractor,
    private val stringSupplier: StringSupplier
) :
    ViewModel() {
    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.Loading)
    val viewState: StateFlow<MainViewState> = _viewState

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            _viewState.value = MainViewState.Loading
            try {
                val creditScoreProcess = reportCreditScoreProgressInteractor()
                val scoreText = creditScoreProcess.score.toString()
                val caption = stringSupplier.getString(R.string.outOf).format(creditScoreProcess.maxScore)
                _viewState.value = MainViewState.Success(
                    scoreText = scoreText,
                    caption = caption,
                    progress = creditScoreProcess.progress
                )
            } catch (e: DataAccessException) {
                _viewState.value = MainViewState.Failure(e.localizedMessage ?: e.toString())
            }
        }
    }

    fun onRetry() {
        load()
    }
}