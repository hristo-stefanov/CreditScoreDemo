package hristostefanov.creditscoredemo.business.dependencies

import hristostefanov.creditscoredemo.business.CreditScore

interface CreditScoreRepository {
    @Throws(DataAccessException::class)
    suspend fun findCreditScore(): CreditScore
}