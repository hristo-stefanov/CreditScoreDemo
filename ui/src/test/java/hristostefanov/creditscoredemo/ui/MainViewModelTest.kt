package hristostefanov.creditscoredemo.ui

import hristostefanov.creditscoredemo.core.business.CreditScoreProgress
import hristostefanov.creditscoredemo.core.business.DataAccessException
import hristostefanov.creditscoredemo.core.business.ReportCreditScoreProgressInteractor
import hristostefanov.creditscoredemo.ui.util.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.willSuspendableAnswer

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    internal val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    internal val coroutinesRule = CoroutinesTestRule()

    @Mock
    private lateinit var reportCreditScoreProgressInteractor: ReportCreditScoreProgressInteractor

    @Mock
    private lateinit var stringSupplier: StringSupplier

    private val viewModelUnderTest by lazy {
        MainViewModel(reportCreditScoreProgressInteractor, stringSupplier)
    }

    @Before
    fun beforeEach() {
        given(stringSupplier.getString(R.string.outOf)).willReturn("out of %1s")
    }

    @Test
    fun success() = runTest(coroutinesRule.testDispatcher) {
        given(reportCreditScoreProgressInteractor()).willSuspendableAnswer {
            delay(10)
            CreditScoreProgress(0.5f, 200, 500, 100)
        }

        viewModelUnderTest // load

        assertThat(viewModelUnderTest.viewState.value).isEqualTo(MainViewState.Loading)
        advanceTimeBy(9)
        assertThat(viewModelUnderTest.viewState.value).isEqualTo(MainViewState.Loading)
        advanceTimeBy(2)
        assertThat(viewModelUnderTest.viewState.value).matches {
            it is MainViewState.Success && it.caption == "out of 500" && it.scoreText == "200"
        }
    }

    @Test
    fun failure() = runTest(coroutinesRule.testDispatcher) {
        given(reportCreditScoreProgressInteractor()).willThrow(DataAccessException("failure"))

        val result = viewModelUnderTest.viewState.first()

        assertThat(result).matches {
            it is MainViewState.Failure && it.message == "failure"
        }
    }

    @Test
    fun interactions() = runTest(coroutinesRule.testDispatcher) {
        given(reportCreditScoreProgressInteractor()).willReturn(
            CreditScoreProgress(0.5f, 200, 500, 100)
        )

        viewModelUnderTest

        then(reportCreditScoreProgressInteractor).should().invoke()
        then(reportCreditScoreProgressInteractor).shouldHaveNoMoreInteractions()
    }
}