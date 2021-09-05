package hristostefanov.creditscoredemo.business.dependencies

import hristostefanov.creditscoredemo.business.DataAccessException

interface CreditScoreRepository {
    @Throws(DataAccessException::class)
    suspend fun findCreditScore(): CreditScore
}