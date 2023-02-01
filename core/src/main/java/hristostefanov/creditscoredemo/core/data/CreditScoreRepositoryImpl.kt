package hristostefanov.creditscoredemo.core.data

import hristostefanov.creditscoredemo.core.business.DataAccessException
import hristostefanov.creditscoredemo.core.business.dependencies.CreditScore
import hristostefanov.creditscoredemo.core.business.dependencies.CreditScoreRepository
import javax.inject.Inject

class CreditScoreRepositoryImpl @Inject constructor(private val service: Service) :
    CreditScoreRepository {
    @Throws(DataAccessException::class)
    override suspend fun findCreditScore(): CreditScore {
        @Suppress("BlockingMethodInNonBlockingContext") // false positive triggered by throws IOException
        return try {
            service.getResponse().creditReportInfo.let { info ->
                CreditScore(
                    id = info.clientRef,
                    score = info.score,
                    minScore = info.minScoreValue,
                    maxScore = info.maxScoreValue,
                    scoreBand = info.scoreBand,
                    scoreChange = info.changedScore
                )
            }
        } catch (e: Exception) {
            throw DataAccessException(e.localizedMessage ?: e.toString(), e)
        }
    }
}