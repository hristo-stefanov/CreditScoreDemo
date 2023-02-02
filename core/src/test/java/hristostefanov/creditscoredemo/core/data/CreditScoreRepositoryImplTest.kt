package hristostefanov.creditscoredemo.core.data

import hristostefanov.creditscoredemo.core.business.DataAccessException
import hristostefanov.creditscoredemo.core.data.models.CreditReportInfo
import hristostefanov.creditscoredemo.core.data.models.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import java.io.IOException

@Suppress("BlockingMethodInNonBlockingContext") // false positive triggered by throwing IOException
@ExperimentalCoroutinesApi
class CreditScoreRepositoryImplTest {

    @get:Rule
    internal val mockitoTest = MockitoJUnit.rule()

    @Mock
    private lateinit var service: Service

    private val repositoryUnderTest by lazy {
        CreditScoreRepositoryImpl(service)
    }

    @Test
    fun success() = runTest {
        val response =
            Response(
                creditReportInfo = CreditReportInfo(
                    score = 200,
                    minScoreValue = 100,
                    maxScoreValue = 500,
                    scoreBand = 3,
                    changedScore = 5
                )
            )
        given(service.getResponse()).willReturn(response)

        val result = repositoryUnderTest.findCreditScore()

        assertThat(result).matches {
            it.maxScore == 500
                    && it.score == 200
                    && it.minScore == 100
                    && it.scoreBand == 3
                    && it.scoreChange == 5
        }
    }

    @Test(expected = DataAccessException::class)
    fun failure() = runTest {
        given(service.getResponse()).willThrow(IOException())

        repositoryUnderTest.findCreditScore()
    }

    @Test
    fun interactions() = runTest {
        val response =
            Response(
                creditReportInfo = CreditReportInfo(
                    score = 200,
                    minScoreValue = 100,
                    maxScoreValue = 500,
                    scoreBand = 3,
                    changedScore = 5
                )
            )
        given(service.getResponse()).willReturn(response)

        repositoryUnderTest.findCreditScore()

        then(service).should().getResponse()
        then(service).shouldHaveNoMoreInteractions()
    }
}