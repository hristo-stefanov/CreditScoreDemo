package hristostefanov.creditscoredemo.core.business

import hristostefanov.creditscoredemo.core.business.dependencies.CreditScore
import hristostefanov.creditscoredemo.core.business.dependencies.CreditScoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
    internal val mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var creditScoreRepository: CreditScoreRepository

    private val interactorUnderTest by lazy {
        ReportCreditScoreProgressInteractorImpl(creditScoreRepository)
    }

    @Test
    fun mediumProgress() = runTest {
        given(creditScoreRepository.findCreditScore()).willReturn(
            CreditScore(
                id = "user123",
                score = 300,
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
    }

    @Test
    fun minimumProgress() = runTest {
        given(creditScoreRepository.findCreditScore()).willReturn(
            CreditScore(
                id = "user123",
                score = 100,
                minScore = 100,
                maxScore = 500,
                scoreBand = 3,
                scoreChange = 0
            )
        )

        val result = interactorUnderTest()

        assertThat(result).matches {
            it.progress == 0.0f && it.minScore == 100 && it.maxScore == 500
        }
    }


    @Test
    fun maximumProgress() = runTest {
        given(creditScoreRepository.findCreditScore()).willReturn(
            CreditScore(
                id = "user123",
                score = 500,
                minScore = 100,
                maxScore = 500,
                scoreBand = 3,
                scoreChange = 0
            )
        )

        val result = interactorUnderTest()

        assertThat(result).matches {
            it.progress == 1.0f && it.minScore == 100 && it.maxScore == 500
        }
    }

    @Test
    fun interactions() = runTest {
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

        interactorUnderTest()

        then(creditScoreRepository).should().findCreditScore()
        then(creditScoreRepository).shouldHaveNoMoreInteractions()
    }
}