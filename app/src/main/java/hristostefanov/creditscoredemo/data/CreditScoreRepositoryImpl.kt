package hristostefanov.creditscoredemo.data

import hristostefanov.creditscoredemo.business.CreditScore
import hristostefanov.creditscoredemo.business.dependencies.CreditScoreRepository
import javax.inject.Inject

class CreditScoreRepositoryImpl @Inject constructor(private val service: Service) : CreditScoreRepository {
    override suspend fun findCreditScore(): CreditScore {
        TODO()
    }
}