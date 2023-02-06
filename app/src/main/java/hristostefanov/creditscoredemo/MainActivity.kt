package hristostefanov.creditscoredemo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import hristostefanov.creditscoredemo.ui.MainScreen
import hristostefanov.creditscoredemo.ui.theme.CreditScoreDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreditScoreDemoTheme {
                MainScreen()
            }
        }
    }
}