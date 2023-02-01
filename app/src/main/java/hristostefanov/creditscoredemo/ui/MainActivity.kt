package hristostefanov.creditscoredemo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import hristostefanov.creditscoredemo.presentation.MainViewModel
import hristostefanov.creditscoredemo.ui.theme.CreditScoreDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()
            val viewState by viewModel.viewState.collectAsStateWithLifecycle()

            CreditScoreDemoTheme {
                MainScreen(viewState, viewModel::onRetry)
            }
        }
    }
}
