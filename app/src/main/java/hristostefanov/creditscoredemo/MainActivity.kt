package hristostefanov.creditscoredemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import hristostefanov.creditscoredemo.presentation.MainViewModel
import hristostefanov.creditscoredemo.ui.MainScreen
import hristostefanov.creditscoredemo.ui.theme.CreditScoreDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val viewState by viewModel.viewState.collectAsState()

            CreditScoreDemoTheme {
                MainScreen(viewState, { viewModel.onRetry() })
            }
        }
    }
}
