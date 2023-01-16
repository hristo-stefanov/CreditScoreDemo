package hristostefanov.creditscoredemo.donut

import androidx.compose.material.Surface
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import org.junit.Rule
import org.junit.Test

class SnapshotTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(screenHeight = 1, softButtons = false),
        renderingMode = SessionParams.RenderingMode.V_SCROLL,
    )

    @Test
    fun test() {
        paparazzi.snapshot {
            Surface {
                DonutWidget(progress = 0.5f, scoreText = "350", caption = "out of 700", title = "Your credit score is" )
            }
        }
    }

}