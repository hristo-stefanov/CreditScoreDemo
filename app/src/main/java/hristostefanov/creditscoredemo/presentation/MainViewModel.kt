package hristostefanov.creditscoredemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hristostefanov.creditscoredemo.business.ReportCreditScoreProgressInteractor
import hristostefanov.creditscoredemo.business.dependencies.CreditScoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(reportCreditScoreProgressInteractor: ReportCreditScoreProgressInteractor) :
    ViewModel() {
    private val _viewState = MutableStateFlow(MainViewState())
    val viewState: StateFlow<MainViewState> = _viewState

    init {
        viewModelScope.launch {
            val creditScoreProcess = reportCreditScoreProgressInteractor()
            val scoreText = creditScoreProcess.score.toString()
            val caption = "out of ${creditScoreProcess.maxScore}"
            _viewState.value = MainViewState(scoreText = scoreText, caption = caption, progress = creditScoreProcess.progress)
        }
    }
}