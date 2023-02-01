package hristostefanov.creditscoredemo.core.business.dependencies

import hristostefanov.creditscoredemo.core.business.DataAccessException

interface CreditScoreRepository {
    @Throws(DataAccessException::class)
    suspend fun findCreditScore(): CreditScore
}