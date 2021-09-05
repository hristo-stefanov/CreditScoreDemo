package hristostefanov.creditscoredemo.business

interface ReportCreditScoreProgressInteractor {
    @Throws(DataAccessException::class)
    suspend operator fun invoke(): CreditScoreProgress
}