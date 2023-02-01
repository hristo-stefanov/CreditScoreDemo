package hristostefanov.creditscoredemo.core.business

interface ReportCreditScoreProgressInteractor {
    @Throws(DataAccessException::class)
    suspend operator fun invoke(): CreditScoreProgress
}