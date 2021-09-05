package hristostefanov.creditscoredemo.data

import hristostefanov.creditscoredemo.business.CreditScore
import hristostefanov.creditscoredemo.business.DataAccessException
import hristostefanov.creditscoredemo.business.dependencies.CreditScoreRepository
import javax.inject.Inject

class CreditScoreRepositoryImpl @Inject constructor(private val service: Service) : CreditScoreRepository {
    @Throws(DataAccessException::class)
    override suspend fun findCreditScore(): CreditScore {
        @Suppress("BlockingMethodInNonBlockingContext") // false positive triggered by throws IOException

        return try {
            service.getResponse().let {
                CreditScore(
                    id = it.creditReportInfo.clientRef,
                    score = it.creditReportInfo.score,
                    minScore = it.creditReportInfo.minScoreValue,
                    maxScore = it.creditReportInfo.maxScoreValue,
                    scoreBand = it.creditReportInfo.scoreBand,
                    scoreChange = it.creditReportInfo.changedScore
                )
            }
        } catch (e: Exception) {
            throw DataAccessException(e.localizedMessage ?: e.toString(), e)
        }
    }
}