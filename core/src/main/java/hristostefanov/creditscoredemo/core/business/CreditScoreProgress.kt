package hristostefanov.creditscoredemo.core.business

data class CreditScoreProgress(
    /**
     * 0.0 represents no progress and 1.0 represents full progress
     */
    val progress: Float,
    val score: Int,
    val maxScore: Int,
    val minScore: Int,
)
