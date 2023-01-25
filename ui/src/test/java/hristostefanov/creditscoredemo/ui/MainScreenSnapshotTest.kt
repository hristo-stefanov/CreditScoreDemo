package hristostefanov.creditscoredemo.ui

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.google.testing.junit.testparameterinjector.TestParameter
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
    fun test(@TestParameter state: State) {
        paparazzi.snapshot {
            CreditScoreDemoTheme {
                MainScreen(state.mainViewState, {})
            }
        }
    }

    // NOTE: using an enum class instead of TestParameterValuesProvider, because objects of
    // sealed classes, such as Empty and Loading contribute to the test name with string
    // representation that include the hash code of the object. The test name is used for naming
    // the snapshots, thus the names of the snapshot become inconsistent across JVM runs.
    // When "data object" Kotlin feature becomes official (experimental in 1.8) we could use
    // TestParameterValuesProvider instead of defining such enum classes.

    @Suppress("unused")
    enum class State(val mainViewState: MainViewState) {
        Success(MainScreenPreviewSuccess),
        Failure(MainScreenPreviewFailure),
        Empty(MainViewState.Empty),
        Loading(MainViewState.Loading)
    }
}