package hristostefanov.creditscoredemo.core.business

import hristostefanov.creditscoredemo.core.business.dependencies.CreditScoreRepository
import javax.inject.Inject

internal class ReportCreditScoreProgressInteractorImpl @Inject constructor(
    private val creditScoreRepository: CreditScoreRepository
) : ReportCreditScoreProgressInteractor {
    @Throws(DataAccessException::class)
    override suspend operator fun invoke(): CreditScoreProgress {
        val creditScore = creditScoreRepository.findCreditScore()
        val progress = (creditScore.score.toFloat() - creditScore.minScore) /
                (creditScore.maxScore - creditScore.minScore)
        return CreditScoreProgress(
            progress = progress,
            score = creditScore.score,
            minScore = creditScore.minScore,
            maxScore = creditScore.maxScore
        )
    }
}