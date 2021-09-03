package hristostefanov.creditscoredemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hristostefanov.creditscoredemo.business.dependencies.CreditScoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(creditScoreRepository: CreditScoreRepository) :
    ViewModel() {
    private val _viewState = MutableStateFlow(MainViewState())
    val viewState: StateFlow<MainViewState> = _viewState

    init {
        viewModelScope.launch {
            _viewState.value = creditScoreRepository.findCreditScore().let {
                val scoreText = it.score.toString()
                val caption = "out of ${it.maxScore}"
                MainViewState(scoreText = scoreText, caption = caption)
            }
        }
    }
}