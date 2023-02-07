package hristostefanov.creditscoredemo.core.business.dependencies

data class CreditScore(
    val id: String,
    val score: Int,
    val minScore: Int,
    val maxScore: Int,
    val scoreBand: Int,
    val scoreChange: Int
)
