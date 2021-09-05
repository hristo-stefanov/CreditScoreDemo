package hristostefanov.creditscoredemo.business

import hristostefanov.creditscoredemo.business.dependencies.CreditScore
import hristostefanov.creditscoredemo.business.dependencies.CreditScoreRepository
import hristostefanov.creditscoredemo.util.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

@ExperimentalCoroutinesApi
class ReportCreditScoreProgressInteractorImplTest {

    @get:Rule
    internal val mockitorRule = MockitoJUnit.rule()

    @get:Rule
    internal val coroutinesRule = CoroutinesTestRule()

    @Mock
    private lateinit var creditScoreRepository: CreditScoreRepository

    private val interactorUnderTest by lazy {
        ReportCreditScoreProgressInteractorImpl(creditScoreRepository)
    }

    @Test
    operator fun invoke() = coroutinesRule.testDispatcher.runBlockingTest {
        given(creditScoreRepository.findCreditScore()).willReturn(
            CreditScore(
                id = "user123",
                score = 200,
                minScore = 100,
                maxScore = 500,
                scoreBand = 3,
                scoreChange = 0
            )
        )

        val result = interactorUnderTest()

        assertThat(result).matches {
            it.progress == 0.5f && it.minScore == 100 && it.maxScore == 500
        }
        then(creditScoreRepository).should().findCreditScore()
        then(creditScoreRepository).shouldHaveNoMoreInteractions()
    }
}