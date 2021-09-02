package hristostefanov.creditscoredemo.business

import hristostefanov.creditscoredemo.business.dependencies.CreditScoreRepository
import hristostefanov.creditscoredemo.util.BaseTestCase
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mock

class ReportCreditScoreProgressInteractorImplTest : BaseTestCase() {

    @Mock
    private lateinit var creditScoreRepository: CreditScoreRepository

    private val interactorUnderTest by lazy {
        ReportCreditScoreProgressInteractorImpl(creditScoreRepository)
    }

    @Test
    operator fun invoke() = runBlockingTest {
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

        Assertions.assertThat(result).matches {
            it.progress == 0.5f && it.minScore == 100 && it.maxScore == 500
        }
    }
}