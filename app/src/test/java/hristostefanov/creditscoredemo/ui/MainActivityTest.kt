package hristostefanov.creditscoredemo.ui

import android.os.Build
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher.Companion.expectValue
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import hristostefanov.creditscoredemo.util.Config as UtilConfig

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O], application = HiltTestApplication::class)
class MainActivityTest {
    private val mockWebServer = MockWebServer()

    @get:Rule(order = 0)
    internal val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val rule = object : TestWatcher() {
        override fun starting(description: Description?) {
            startMockWebServer()
        }

        override fun finished(description: Description?) {
            stopMockWebServer()
        }
    }

    @get:Rule(order = 2)
    val androidComposeRule = createAndroidComposeRule<MainActivity>()

    @LargeTest // end-to-end test
    @Test
    fun happyPath() {
        androidComposeRule.onNodeWithText("Your credit score is").assertIsDisplayed()
        androidComposeRule.onNodeWithText("out of 700").assertIsDisplayed()
        androidComposeRule.onNodeWithText("514").assertIsDisplayed()

        androidComposeRule.onNodeWithTag("Progress indicator").assert(
            expectValue(
                SemanticsProperties.ProgressBarRangeInfo,
                ProgressBarRangeInfo(0.7342857f, 0f..1f)
            )
        )
    }

    private fun startMockWebServer() {
        val context = ApplicationProvider.getApplicationContext<HiltTestApplication>()

        val response = context.assets.open("response.json").bufferedReader().use {
            it.readText()
        }

        mockWebServer.enqueue(MockResponse().setBody(response))

        mockWebServer.start()

        val baseURL = mockWebServer.url("/")
        UtilConfig.SERVICE_BASE_URL = baseURL.toString()
    }

    private fun stopMockWebServer() {
        mockWebServer.shutdown()
    }
}
