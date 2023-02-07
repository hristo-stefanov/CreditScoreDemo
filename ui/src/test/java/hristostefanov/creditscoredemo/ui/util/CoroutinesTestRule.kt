package hristostefanov.creditscoredemo.ui.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

// Originating from: https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471
// NOTE: "we call the runBlockingTest method inside the TestCoroutineDispatcher that the
// rule creates. Since that Dispatcher overrides Dispatchers.Main, MainViewModel will run the
// coroutine on that Dispatcher too. Calling runBlockingTest will make that coroutine to execute
// synchronously in the test."
//
// See also: https://developer.android.com/kotlin/coroutines/test

@ExperimentalCoroutinesApi
class CoroutinesTestRule(
    // Note: UnconfinedTestDispatcher eagerly runs new coroutines, unlike StandardTestDispatcher
    // which requires some way of explicit yielding for precise control.
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}