package hristostefanov.creditscoredemo.business

interface ReportCreditScoreProgressInteractor {
    suspend operator fun invoke(): CreditScoreProgress
}