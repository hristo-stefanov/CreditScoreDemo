package hristostefanov.creditscoredemo.data

import hristostefanov.creditscoredemo.business.CreditScore
import hristostefanov.creditscoredemo.business.dependencies.CreditScoreRepository
import javax.inject.Inject

class CreditScoreRepositoryImpl @Inject constructor(private val service: Service) : CreditScoreRepository {
    override suspend fun findCreditScore(): CreditScore {
        return service.getResponse().let {
            CreditScore(
                id = it.creditReportInfo.clientRef,
                score = it.creditReportInfo.score,
                minScore = it.creditReportInfo.minScoreValue,
                maxScore = it.creditReportInfo.maxScoreValue,
                scoreBand = it.creditReportInfo.scoreBand,
                scoreChange = it.creditReportInfo.changedScore
            )
        }
    }
}