package hristostefanov.creditscoredemo.presentation

import hristostefanov.creditscoredemo.business.CreditScore
import hristostefanov.creditscoredemo.business.dependencies.CreditScoreRepository
import hristostefanov.creditscoredemo.util.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

@ExperimentalCoroutinesApi
class MainViewModelTest() {

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var creditScoreRepository: CreditScoreRepository

    private val viewModelUnderTest by lazy {
        MainViewModel(creditScoreRepository)
    }

    @Test
    fun getViewState() = coroutinesTestRule.testDispatcher.runBlockingTest {
        given(creditScoreRepository.findCreditScore()).willReturn(
            CreditScore("user123", 200, 100, 500, 3, 5)
        )

        val result = viewModelUnderTest.viewState.first()

        assertThat(result).matches {
            it.caption == "out of 500" && it.scoreText == "200"
        }
        then(creditScoreRepository).should().findCreditScore()
        then(creditScoreRepository).shouldHaveNoMoreInteractions()
    }
}