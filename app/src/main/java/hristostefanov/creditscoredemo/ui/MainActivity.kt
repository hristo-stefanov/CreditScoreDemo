package hristostefanov.creditscoredemo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import hristostefanov.creditscoredemo.presentation.MainViewModel
import hristostefanov.creditscoredemo.presentation.MainViewState
import hristostefanov.creditscoredemo.ui.theme.CreditScoreDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewStateFlow = viewModel.viewState
            val lifecycleOwner = LocalLifecycleOwner.current

            val viewStateLifecycleAwareFlow = remember(viewStateFlow, lifecycleOwner) {
                viewStateFlow.flowWithLifecycle(lifecycleOwner.lifecycle)
            }

            val viewState by viewStateLifecycleAwareFlow.collectAsState(MainViewState.Empty)

            CreditScoreDemoTheme {
                MainScreen(viewState) {
                    viewModel.onRetry()
                }
            }
        }
    }
}
