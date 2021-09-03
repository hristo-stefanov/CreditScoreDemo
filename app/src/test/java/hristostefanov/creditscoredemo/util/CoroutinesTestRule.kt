package hristostefanov.creditscoredemo.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

// Originating from: https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471
// NOTE: "we call the runBlockingTest method inside the TestCoroutineDispatcher that the
// rule creates. Since that Dispatcher overrides Dispatchers.Main, MainViewModel will run the
// coroutine on that Dispatcher too. Calling runBlockingTest will make that coroutine to execute
// synchronously in the test."

@ExperimentalCoroutinesApi
class CoroutinesTestRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}