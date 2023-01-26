package hristostefanov.creditscoredemo.data

import hristostefanov.creditscoredemo.business.DataAccessException
import hristostefanov.creditscoredemo.data.models.CreditReportInfo
import hristostefanov.creditscoredemo.data.models.Response
import hristostefanov.creditscoredemo.util.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
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

    @get:Rule
    internal val coroutinesRule = CoroutinesTestRule()

    @Mock
    private lateinit var service: Service

    private val repositoryUnderTest by lazy {
        CreditScoreRepositoryImpl(service)
    }

    @Test
    fun success() = runTest(coroutinesRule.testDispatcher) {
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

        Assertions.assertThat(result).matches {
            it.maxScore == 500
                    && it.score == 200
                    && it.minScore == 100
                    && it.scoreBand == 3
                    && it.scoreChange == 5
        }
    }

    @Test(expected = DataAccessException::class)
    fun failure() = runTest(coroutinesRule.testDispatcher) {
        given(service.getResponse()).willThrow(IOException())

        repositoryUnderTest.findCreditScore()
    }

    @Test
    fun interactions() = runTest(coroutinesRule.testDispatcher) {
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