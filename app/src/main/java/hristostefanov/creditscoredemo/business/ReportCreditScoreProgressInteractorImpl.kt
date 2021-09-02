package hristostefanov.creditscoredemo.business

import hristostefanov.creditscoredemo.business.dependencies.CreditScoreRepository
import javax.inject.Inject

class ReportCreditScoreProgressInteractorImpl @Inject constructor(
    creditScoreRepository: CreditScoreRepository
) : ReportCreditScoreProgressInteractor {
    override fun invoke(): CreditScoreProgress {
        TODO("Not yet implemented")
    }
}