package hristostefanov.creditscoredemo.ui

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import hristostefanov.creditscoredemo.ui.theme.CreditScoreDemoTheme
import org.junit.Rule
import org.junit.Test

class MainScreenSnapshotTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(softButtons = false)
    )

    @Test
    fun test() {
        paparazzi.snapshot {
            CreditScoreDemoTheme {
                MainScreen(
                    viewState = MainViewState.Success(
                        "350",
                        "out of 700",
                        0.5f
                    )
                ) {}
            }
        }
    }
}