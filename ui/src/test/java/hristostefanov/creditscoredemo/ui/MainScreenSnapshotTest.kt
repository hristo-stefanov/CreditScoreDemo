package hristostefanov.creditscoredemo.ui

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameter.TestParameterValuesProvider
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import hristostefanov.creditscoredemo.ui.theme.CreditScoreDemoTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class MainScreenSnapshotTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(softButtons = false)
    )

    // NOTE: Paparazzi does not support .gif() or .snapshot(offsetMillis) for Composables yet.
    // Paparazzi does not support ComposeView neither.
    // Thus the snapshot of the Loading state, which doesn't look right, cannot be advanced
    // in time to capture a more useful animation frame.

    @Test
    fun test(@TestParameter(valuesProvider = ViewStateProvider::class) viewState: MainViewState) {
        paparazzi.snapshot {
            CreditScoreDemoTheme {
                MainScreen(viewState) {}
            }
        }
    }

    private class ViewStateProvider : TestParameterValuesProvider {
        override fun provideValues(): List<MainViewState> {
            return MainScreenPreviewStates
        }
    }
}